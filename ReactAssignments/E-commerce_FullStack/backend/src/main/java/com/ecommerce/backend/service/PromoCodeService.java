package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.promocode.PromoCodeRequestDTO;
import com.ecommerce.backend.dto.promocode.PromoCodeResponseDTO;

import java.util.List;

public interface PromoCodeService {

    PromoCodeResponseDTO createPromoCode(PromoCodeRequestDTO promoCodeRequestDTO);

    PromoCodeResponseDTO updatePromoCode(Long id, PromoCodeRequestDTO promoCodeRequestDTO);

    void deletePromoCode(Long id);

    List<PromoCodeResponseDTO> getAllPromoCodes();

    PromoCodeResponseDTO getPromoCodeById(Long id);

    PromoCodeResponseDTO validatePromo(String code);
}
