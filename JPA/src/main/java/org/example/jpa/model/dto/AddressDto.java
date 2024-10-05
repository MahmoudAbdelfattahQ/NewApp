package org.example.jpa.model.dto;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class AddressDto {

    private String apartmentNumber;
    private String homeNumber;
    private String street;
    private String city;
    private String state;
    private String country;

}
