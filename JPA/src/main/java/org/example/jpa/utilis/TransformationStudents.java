package org.example.jpa.utilis;

import org.example.jpa.model.dto.AddressDto;
import org.example.jpa.model.dto.StudentDto;

import org.example.jpa.model.entity.Address;
import org.example.jpa.model.entity.Student;

import java.util.Optional;
import java.util.UUID;

import static org.example.jpa.utilis.TransformationAddress.*;


public class TransformationStudents {

    private TransformationStudents() {

    }

    public static StudentDto toStudentDto(Student student) {

        Optional<AddressDto> addressDtoOptional = toAddressDto(student.getAddress());

        StudentDto.StudentDtoBuilder studentDtoBuilder = StudentDto.builder()
                .name(student.getName())
                .email(student.getEmail())
                .age(student.getAge())
                .marks(student.getMarks())
                .phoneNumber(student.getPhoneNumber());

        addressDtoOptional.ifPresent(studentDtoBuilder::addressDto);

        return  studentDtoBuilder.build();
    }

    public static Student toStudent(StudentDto studentDto) {

        Optional<Address> addressOptional = toAddress(studentDto.getAddressDto());

        Student.StudentBuilder studentBuilder = Student.builder()
                .id(UUID.randomUUID())
                .name(studentDto.getName())
                .email(studentDto.getEmail())
                .age(studentDto.getAge())
                .marks(studentDto.getMarks())
                .phoneNumber(studentDto.getPhoneNumber())
                .idAddress(addressOptional.get().getId());
        addressOptional.ifPresent(studentBuilder::address);
        return  studentBuilder.build();
    }
}
