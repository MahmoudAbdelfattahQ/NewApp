package org.example.jpa.service;

import org.example.jpa.model.dto.StudentDto;
import org.example.jpa.model.entity.Student;

import java.util.List;
import java.util.UUID;

public interface ISudentService {

    void save(StudentDto studentDto);

    void update(StudentDto studentDto);

    void deleteById(UUID id);

    void deleteAll();

    StudentDto getStudent(UUID id);

    List<StudentDto> getAllStudents();

    Long countStudents();


}
