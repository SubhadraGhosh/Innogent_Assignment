package com.ecommerce.backend.dao;

import com.ecommerce.backend.entity.Address;
import java.util.List;

public interface AddressDao {

    // create
    Address save(Address address);

    // checking
    List<Address> findByUserId(Long userId);
}
