package org.example.jpa.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.jpa.model.entity.Address;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {

    private String name;
    private String email;
    private int age;
    private float marks;
    private String phoneNumber;
    private AddressDto addressDto;

}
