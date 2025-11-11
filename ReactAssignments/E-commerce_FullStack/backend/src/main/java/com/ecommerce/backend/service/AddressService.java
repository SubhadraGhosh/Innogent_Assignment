package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.address.AddressRequestDTO;
import com.ecommerce.backend.dto.address.AddressResponseDTO;
import java.util.List;

public interface AddressService {

    // create
    AddressResponseDTO addAddress(AddressRequestDTO requestDTO);

    // checking
    List<AddressResponseDTO> getAddressesByUser(Long userId);
}
