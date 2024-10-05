package com.example.NewApp.dao;

import com.example.NewApp.model.dto.StudentDto;
import com.example.NewApp.model.entity.Student;

import java.util.Collection;
import java.util.Map;

public interface IStudentRepo {

    Map<String, StudentDto> getStudents();

    void insertNewStudent(StudentDto studentDto);

    void updateStudent(String key, StudentDto studentDto);

    void deleteStudent(String key);

    void deleteAllStudents();

    int getCount();

    void clear();

    Collection<Student> getAllStudents();



}
