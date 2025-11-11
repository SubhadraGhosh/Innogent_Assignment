package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dao.PromoCodeDao;
import com.ecommerce.backend.dto.promocode.PromoCodeRequestDTO;
import com.ecommerce.backend.dto.promocode.PromoCodeResponseDTO;
import com.ecommerce.backend.entity.PromoCode;
import com.ecommerce.backend.mapper.PromoCodeMapper;
import com.ecommerce.backend.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromoCodeServiceImpl implements PromoCodeService {

    @Autowired
    private final PromoCodeDao promoCodeDao;

    @Autowired
    private final PromoCodeMapper promoCodeMapper;



    @Override
    public PromoCodeResponseDTO createPromoCode(PromoCodeRequestDTO dto) {
        PromoCode promoCode = promoCodeMapper.toEntity(dto);
        PromoCode saved = promoCodeDao.save(promoCode);
        return promoCodeMapper.toDto(saved);
    }

    @Override
    public PromoCodeResponseDTO updatePromoCode(Long id, PromoCodeRequestDTO dto) {
        PromoCode existingPromo = promoCodeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Promo code not found with ID: " + id));

        existingPromo.setCode(dto.getCode());
        existingPromo.setDiscountType(dto.getDiscountType());
        existingPromo.setDiscountValue(dto.getDiscountValue());
        existingPromo.setExpiryDate(dto.getExpiryDate());
        existingPromo.setActive(dto.isActive());

        PromoCode updated = promoCodeDao.save(existingPromo);
        return promoCodeMapper.toDto(updated);
    }

    @Override
    public void deletePromoCode(Long id) {
        promoCodeDao.deleteById(id);
    }

    @Override
    public List<PromoCodeResponseDTO> getAllPromoCodes() {
        return promoCodeDao.findAll()
                .stream()
                .map(promoCodeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PromoCodeResponseDTO getPromoCodeById(Long id) {
        PromoCode promoCode = promoCodeDao.findById(id)
                .orElseThrow(() -> new RuntimeException("Promo code not found with ID: " + id));
        return promoCodeMapper.toDto(promoCode);
    }

    @Override
    public PromoCodeResponseDTO validatePromo(String code) {
        PromoCode promoCode = promoCodeDao.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Invalid promo code"));

        if (!promoCode.isActive()) {
            throw new RuntimeException("Promo code is inactive");
        }

        if (promoCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Promo code expired");
        }

        return promoCodeMapper.toDto(promoCode);
    }
}
