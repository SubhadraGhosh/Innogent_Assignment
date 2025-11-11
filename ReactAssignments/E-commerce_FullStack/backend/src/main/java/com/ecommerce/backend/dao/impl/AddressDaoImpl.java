package com.ecommerce.backend.dao.impl;

import com.ecommerce.backend.dao.AddressDao;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AddressDaoImpl implements AddressDao {

    private final AddressRepository addressRepository;

    /**
     * Save a new address entity
     */
    @Override
    public Address save(Address address) {
        log.info("Saving new address to database for userId: {}", address.getUserId());
        Address saved = addressRepository.save(address);
        log.info("Address saved successfully with id: {}", saved.getId());
        return saved;
    }


    //Fetch all addresses by userId
    @Override
    public List<Address> findByUserId(Long userId) {

        log.info("Fetching all addresses for userId: {}", userId);
        List<Address> list = addressRepository.findByUserId(userId);
        log.info("Fetched {} addresses for userId: {}", list.size(), userId);
        return list;
    }
}
