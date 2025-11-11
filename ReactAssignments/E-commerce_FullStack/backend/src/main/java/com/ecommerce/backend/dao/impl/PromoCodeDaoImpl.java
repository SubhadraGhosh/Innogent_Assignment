package com.ecommerce.backend.dao.impl;

import com.ecommerce.backend.dao.PromoCodeDao;
import com.ecommerce.backend.entity.PromoCode;
import com.ecommerce.backend.repository.PromoCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PromoCodeDaoImpl implements PromoCodeDao {

    @Autowired
    private final PromoCodeRepository promoCodeRepository;

    @Override
    public PromoCode save(PromoCode promoCode) {
        return promoCodeRepository.save(promoCode);
    }

    @Override
    public List<PromoCode> findAll() {
        return promoCodeRepository.findAll();
    }

    @Override
    public Optional<PromoCode> findById(Long id) {
        return promoCodeRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        promoCodeRepository.deleteById(id);
    }

    @Override
    public Optional<PromoCode> findByCode(String code) {
        return promoCodeRepository.findByCode(code);
    }
}
