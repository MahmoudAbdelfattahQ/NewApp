package com.example.NewApp.service;

import com.example.NewApp.model.dto.StudentDto;

import java.util.Collection;
import java.util.Map;

public interface IStudentService {

    Map<String, StudentDto> getStudentDtoMap();

    void insertStudentDto(StudentDto studentDto);

    void updateStudentDto(String key, StudentDto studentDto);

     void deleteStudentDto(String key) ;

    void clearAllStudentDto();

    Collection<StudentDto> getAllStudentDto();

    // Optional<StudentDto> getFirstStudent();

}

