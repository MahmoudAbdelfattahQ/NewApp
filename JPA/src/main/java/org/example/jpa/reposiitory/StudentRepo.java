package org.example.jpa.reposiitory;

import org.example.jpa.model.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentRepo extends JpaRepository<Student, UUID> {


    Student findByEmail(String email);


}
