package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.address.AddressRequestDTO;
import com.ecommerce.backend.dto.address.AddressResponseDTO;
import com.ecommerce.backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;

    // create - add new address
    @PostMapping("/add")
    public ResponseEntity<AddressResponseDTO> addAddress(@RequestBody AddressRequestDTO requestDTO) {
        AddressResponseDTO response = addressService.addAddress(requestDTO);
        return ResponseEntity.ok(response);
    }

    // checking - get all addresses for user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AddressResponseDTO>> getAddressesByUser(@PathVariable Long userId) {
        List<AddressResponseDTO> response = addressService.getAddressesByUser(userId);
        return ResponseEntity.ok(response);
    }
}
