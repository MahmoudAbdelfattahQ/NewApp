package org.example.jpa.service;

import org.example.jpa.model.dto.StudentDto;
import org.example.jpa.model.entity.Address;
import org.example.jpa.model.entity.Student;
import org.example.jpa.reposiitory.StudentRepo;
import org.example.jpa.utilis.TransformationStudents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static org.example.jpa.utilis.TransformationStudents.toStudent;

@Component
public class StudentService implements ISudentService{


    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepo studentRepo;


    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;

    }

    @Override
    public void save(StudentDto studentDto) {
          studentRepo.save(toStudent(studentDto));
    }

    @Override
    public void update(StudentDto studentDto) {
        Student student = studentRepo.findByEmail(studentDto.getEmail());
        student.setName(studentDto.getName());
        student.setAge(studentDto.getAge());
        student.setMarks(studentDto.getMarks());
        student.setPhoneNumber(studentDto.getPhoneNumber());
        studentRepo.save(student);
        log.info("Updating student by email {}",studentDto.getEmail());
    }

    @Override
    public void deleteById(UUID id) {
        studentRepo.deleteById(id);

    }

    @Override
    public void deleteAll() {
        studentRepo.deleteAll();

    }

    @Override
    public StudentDto getStudent(UUID id) {
        Student student = studentRepo.findById(id).get();
        return TransformationStudents.toStudentDto(student);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students =  studentRepo.findAll();
        return students.stream().map(TransformationStudents::toStudentDto).toList();
    }

    @Override
    public Long countStudents() {
      return   studentRepo.count();
    }
}