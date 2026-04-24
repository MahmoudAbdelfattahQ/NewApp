# Design Review: Settlement Service File Download Enhancement

## Overall assessment
The proposed approach is directionally good: persisting file download state in DB and using a periodic retry worker is the right pattern for reliability and restart safety. The weakest points are currently in **DB normalization/constraints** and **state machine semantics/idempotency**.

---

## DB Design Review (focus)

### What is good
- Explicit status tracking per file is correct for replay/retry resilience.
- Storing `size_bytes`, `retries`, and timestamps supports stability checks and observability.
- Having one table for workflow state can simplify worker logic.

### Gaps / risks and recommended changes

1. **Missing uniqueness/idempotency constraints**
   - Risk: duplicate rows for same `(cycle_id, file_type, file_path)` from redelivery.
   - Recommendation:
     - Add unique constraint: `UNIQUE (cycle_id, file_type)` if path is deterministic per type.
     - If path can vary, use `UNIQUE (cycle_id, file_type, file_path)`.

2. **`file_path_msg` likely denormalized/ambiguous**
   - Risk: storing a "full path DTO" in same row can blur boundaries and duplicate message payload.
   - Recommendation:
     - Split into a header table (message/cycle level) and detail table (file level):
       - `settlement_cycle` (one row per cycle/message)
       - `file_download_status` (one row per file type)
     - Keep raw payload in a JSON column only if needed for audit/troubleshooting.

3. **No lock/version column for concurrent workers**
   - Risk: two worker instances can process same row simultaneously.
   - Recommendation:
     - Add `version` column (optimistic locking) **or** atomic claim fields (`processing_owner`, `processing_until`).

4. **Status-only model is missing schedule fields**
   - Risk: periodic scan can repeatedly hit rows too early, causing noisy retries.
   - Recommendation:
     - Add `next_retry_at` and select only due rows.
     - Optional: `last_error_code`, `last_error_message` for diagnostics.

5. **Retry counters need clearer semantics**
   - Risk: retries incremented for metadata, download, and stability mismatches without distinction can exhaust attempts too early.
   - Recommendation:
     - Keep either separate counters (`metadata_retries`, `download_retries`, `stability_retries`) or one counter with well-defined increment rules.

6. **Index strategy not defined**
   - Recommendation:
     - Index `(file_download_status, next_retry_at)` for worker scans.
     - Index `cycle_id` and `(cycle_id, file_type)` for reconciliation sequence queries.

7. **Lifecycle timestamps incomplete**
   - Recommendation:
     - Add `first_downloaded_at`, `second_verified_at`, `failed_at` to simplify SLA/reporting and debugging.

---

## State Machine Review (focus)

### What is good
- `PENDING -> DOWNLOADED_FIRST -> DOWNLOADED_SECOND` maps well to stability validation.
- Explicit terminal states (`FAILED`, `DISCARDED`) are useful.

### Ambiguities / corrections needed

1. **`DOWNLOADING` may be unnecessary in timer-based workers**
   - If download is synchronous and short, you can claim + process in one transaction and avoid persistent `DOWNLOADING`.
   - If kept, define timeout recovery (`DOWNLOADING` stuck rows after N minutes -> back to retry state).

2. **Transition rules are underspecified**
   - Define exact legal transitions only (enforced in code):
     - `PENDING -> DOWNLOADED_FIRST | FAILED`
     - `DOWNLOADED_FIRST -> DOWNLOADED_SECOND | PENDING | FAILED | DISCARDED`
     - No backward transition from `DOWNLOADED_SECOND`.

3. **Stability check logic should not always redownload**
   - Better approach:
     - First pass: capture remote size + mtime and download.
     - Second pass: re-read remote metadata only; if unchanged, mark stable and proceed.
   - Redownload only when metadata changed or policy requires content verification.

4. **State naming can be clearer**
   - Consider:
     - `DOWNLOADED_FIRST` -> `AWAITING_STABILITY_CHECK`
     - `DOWNLOADED_SECOND` -> `STABLE`
   - This reduces confusion when the second step is mostly metadata verification.

5. **`FAILED` vs `DISCARDED` policy needs hard rules**
   - Example:
     - `FAILED` = technical/transient retries exhausted.
     - `DISCARDED` = business-invalid or unrecoverable data issue.

6. **Idempotent handoff missing**
   - Before publishing to `ReconciliationTrigger`, ensure one-time handoff via:
     - outbox table + dispatcher, or
     - `handoff_sent_at` with transactional update + dedupe key.

---

## Recommended minimal schema (practical)

- `file_download_status`
  - `id` PK
  - `cycle_id` varchar not null
  - `file_type` enum not null
  - `file_path` varchar not null
  - `status` enum not null
  - `size_bytes` bigint null
  - `remote_mtime` timestamp null
  - `retries` int not null default 0
  - `next_retry_at` timestamp not null
  - `last_error_code` varchar null
  - `last_error_message` varchar null
  - `version` bigint not null default 0
  - `created_at`, `updated_at` not null
  - `first_downloaded_at`, `second_verified_at`, `failed_at` null
  - `handoff_sent_at` timestamp null
  - Unique `(cycle_id, file_type)`

---

## Processing recommendations

1. Insert/Upsert rows idempotently from listener.
2. Worker picks due rows (`status in (PENDING, DOWNLOADED_FIRST)` and `next_retry_at <= now`) with row claim/lock.
3. Enforce order per cycle: `trx -> transaction -> summary` by querying prerequisites.
4. Transition with strict compare-and-set updates (`where id=? and status=? and version=?`).
5. Publish reconciliation event only when all three file types are `DOWNLOADED_SECOND/STABLE`, and do it idempotently.

---

## Final verdict
- **Good architectural direction**, but before implementation you should harden:
  1) uniqueness/idempotency,
  2) concurrency control,
  3) precise transition contract,
  4) retry scheduling (`next_retry_at`), and
  5) idempotent handoff to reconciliation queue.

Without these, production behavior will likely show duplicates, race conditions, and inconsistent retries.
