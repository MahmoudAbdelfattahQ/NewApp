package com.example.NewApp.dao;


import com.example.NewApp.model.dto.StudentDto;
import com.example.NewApp.model.entity.Student;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Component
public class StudentRepoImpl implements IStudentRepo {

    private final Map<String, Student> studentMap;
    private int id = 0;

    public StudentRepoImpl() {
        studentMap = new HashMap<>();
    }


    @Override
    public Map<String, StudentDto> getStudents() {
        return Map.of();
    }

    @Override
    public void insertNewStudent(StudentDto studentDto) {
        id++;
        studentMap.put(String.valueOf(id), studentDto);
        System.out.println(studentMap.get(String.valueOf(id)) + " id : " );

    }

    @Override
    public void updateStudent(String key, StudentDto studentDto) {
        studentMap.put(key, studentDto);
        System.out.println(" id : " + key);
        System.out.println(studentDto);
    }

    @Override
    public void deleteStudent(String key) {
        studentMap.remove(key);
    }

    @Override
    public void deleteAllStudents() {
        studentMap.values().forEach(studentMap::remove);
    }

    @Override
    public int getCount() {
        return studentMap.size();
    }

    @Override
    public void clear() {
        studentMap.clear();
    }

    @Override
    public Collection<Student> getAllStudents() {
        return new HashSet<>(studentMap.values());
    }
}
