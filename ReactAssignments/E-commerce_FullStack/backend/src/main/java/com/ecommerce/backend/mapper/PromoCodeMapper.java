package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.promocode.*;
import com.ecommerce.backend.entity.PromoCode;
import org.springframework.stereotype.Component;

@Component
public class PromoCodeMapper {

    public PromoCode toEntity(PromoCodeRequestDTO dto) {
        return PromoCode.builder()
                .code(dto.getCode())
                .discountType(dto.getDiscountType())
                .discountValue(dto.getDiscountValue())
                .expiryDate(dto.getExpiryDate())
                .active(dto.isActive())
                .build();
    }

    public PromoCodeResponseDTO toDto(PromoCode promoCode) {
        return PromoCodeResponseDTO.builder()
                .id(promoCode.getId())
                .code(promoCode.getCode())
                .discountType(promoCode.getDiscountType())
                .discountValue(promoCode.getDiscountValue())
                .expiryDate(promoCode.getExpiryDate())
                .active(promoCode.isActive())
                .build();
    }
}
