package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.address.AddressRequestDTO;
import com.ecommerce.backend.dto.address.AddressResponseDTO;
import com.ecommerce.backend.entity.Address;

public class AddressMapper {

    // create
    public static Address toEntity(AddressRequestDTO dto) {
        return Address.builder()
                .userId(dto.getUserId())
                .fullName(dto.getFullName())
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .country(dto.getCountry())
                .phone(dto.getPhone())
                .build();
    }

    // convert to response
    public static AddressResponseDTO toDTO(Address entity) {
        return AddressResponseDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .street(entity.getStreet())
                .city(entity.getCity())
                .state(entity.getState())
                .pincode(entity.getPincode())
                .country(entity.getCountry())
                .phone(entity.getPhone())
                .build();
    }
}
