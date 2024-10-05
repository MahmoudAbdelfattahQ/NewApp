package com.example.NewApp.service;
import com.example.NewApp.dao.StudentRepoImpl;
import com.example.NewApp.model.dto.StudentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StudentServiceImpl  implements IStudentService {
    private final StudentRepoImpl studentRepo;

    @Autowired
    public StudentServiceImpl(StudentRepoImpl studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
   public  Map<String, StudentDto> getStudentDtoMap() {
        return Map.of();
    }

    @Override
    public void insertStudentDto(StudentDto studentDto) {
        studentRepo.insertNewStudent(studentDto);
    }

    @Override
    public void updateStudentDto(String key, StudentDto studentDto) {
        studentRepo.updateStudent(key, studentDto);

    }

    @Override
    public void deleteStudentDto(String key) {
        studentRepo.deleteStudent(key);
    }


    @Override
    public void clearAllStudentDto() {
        studentRepo.deleteAllStudents();
    }

    @Override
    public Collection<StudentDto> getAllStudentDto() {

        return studentRepo.getAllStudents().stream()
                .map(student ->
                        new StudentDto( student.getName(),student.getEmail(), student.getAge()))
                .collect(Collectors.toSet());
    }

 /*   @Override
    public Optional<StudentDto> getFirstStudent() {
        return studentRepo.
    }*/
}
