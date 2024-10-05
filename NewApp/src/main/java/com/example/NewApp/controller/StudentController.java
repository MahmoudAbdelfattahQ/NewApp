package com.example.NewApp.controller;

import com.example.NewApp.model.dto.StudentDto;
import com.example.NewApp.service.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {


    private final StudentServiceImpl studentService;
    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/getStudents")
    public Collection<StudentDto> getStudents() {
        return  studentService.getAllStudentDto();
    }

    @PostMapping("/insert")
    public void insertStudent(@RequestBody StudentDto studentDto) {
        System.out.println(studentDto);
        studentService.insertStudentDto(studentDto);
    }

 /*   @GetMapping("/{id}")
    public StudentDto findStudentById(@PathVariable int id) {

    }*/

    @PutMapping("/update/{id}")
    public void updateStudent(@PathVariable int id, @RequestBody StudentDto studentDto) {

        studentService.updateStudentDto(String.valueOf(id),studentDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable int id) {
        studentService.deleteStudentDto(String.valueOf(id));
    }

}
