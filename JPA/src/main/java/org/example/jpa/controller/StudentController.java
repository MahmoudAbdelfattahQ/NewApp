package org.example.jpa.controller;


import org.example.jpa.model.dto.StudentDto;
import org.example.jpa.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("save")
    public ResponseEntity<String> save(@RequestBody StudentDto studentDto) {
        studentService.save(studentDto);
        return ResponseEntity.ok("Student saved successfully!");
    }

    @PutMapping ("update")
    public void update(@RequestBody StudentDto studentDto ) {
       log.info("Updating student with email: " );
        studentService.update(studentDto);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable UUID id) {
        studentService.deleteById(id);
    }

    @DeleteMapping("/deleteAll/")
    public void deleteAll() {
        studentService.deleteAll();
    }

    @GetMapping("findByID/{id}")
    public StudentDto findById(@PathVariable UUID id) {
        return studentService.getStudent(id);

    }


    @GetMapping("getAllStudent")
    public List<StudentDto> getAllStudent() {
       return studentService.getAllStudents();
    }

    @GetMapping("getCount")
    public long count(){
       return studentService.countStudents();
    }

}
