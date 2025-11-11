package com.ecommerce.backend.dao;

import com.ecommerce.backend.entity.PromoCode;
import java.util.List;
import java.util.Optional;

public interface PromoCodeDao {

    PromoCode save(PromoCode promoCode);

    List<PromoCode> findAll();

    Optional<PromoCode> findById(Long id);

    void deleteById(Long id);

    Optional<PromoCode> findByCode(String code);
}
