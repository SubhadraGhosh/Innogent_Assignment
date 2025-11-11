package com.ecommerce.backend.dto.address;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDTO {
    private Long id;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String country;
    private String phone;
}
