package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dao.AddressDao;
import com.ecommerce.backend.dto.address.AddressRequestDTO;
import com.ecommerce.backend.dto.address.AddressResponseDTO;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.mapper.AddressMapper;
import com.ecommerce.backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    // create - add new address
    @Override
    @Transactional
    public AddressResponseDTO addAddress(AddressRequestDTO requestDTO) {
        // convert dto → entity
        Address address = AddressMapper.toEntity(requestDTO);

        // save to db using dao
        Address saved = addressDao.save(address);

        // convert entity → response dto
        return AddressMapper.toDTO(saved);
    }

    // checking - get all address by user id
    @Override
    @Transactional(readOnly = true)
    public List<AddressResponseDTO> getAddressesByUser(Long userId) {
        // fetch from dao
        List<Address> addresses = addressDao.findByUserId(userId);

        // convert list of entities to dto
        return addresses.stream()
                .map(AddressMapper::toDTO)
                .collect(Collectors.toList());
    }
}
