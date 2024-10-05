package org.example.jpa.utilis;

import org.example.jpa.model.dto.AddressDto;
import org.example.jpa.model.entity.Address;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class TransformationAddress {

    private TransformationAddress() {
    }

    public static Optional<AddressDto> toAddressDto(Address address) {
          if (Objects.isNull(address)) {
              return   Optional.empty();
        }
        else {
              return Optional.of(new AddressDto(address.getApartmentNumber(),
                      address.getHomeNumber(),
                      address.getStreet(),
                      address.getCity(),
                      address.getState(),
                      address.getCountry()));
        }
    }

    public static Optional<Address> toAddress(AddressDto addressDto) {
        if (Objects.isNull(addressDto))
            return Optional.empty();

        else
            return Optional.of(Address.builder()
                    .id(UUID.randomUUID())
                    .apartmentNumber(addressDto.getApartmentNumber())
                    .homeNumber(addressDto.getHomeNumber())
                    .state(addressDto.getState())
                    .city(addressDto.getCity())
                    .country(addressDto.getCountry())
                    .street(addressDto.getStreet())
                    .build());
    }


}
