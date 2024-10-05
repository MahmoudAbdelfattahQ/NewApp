package com.example.NewApp.delegation;

import lombok.extern.slf4j.Slf4j;
/**
 * Delegated Class
 * @author Mahmoud Abdelfattah
 */

@Slf4j
public class RealPrinter implements Printer{
    @Override
    public void print(String message) {
        log.info("print from delegated class as RealPrinter ,{}" ,message);
    }
}
