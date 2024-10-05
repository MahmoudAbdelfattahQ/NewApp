package com.example.NewApp.model.dto;

import com.example.NewApp.model.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends Student {
    private String name;
    private String email;
    private int age;
}
