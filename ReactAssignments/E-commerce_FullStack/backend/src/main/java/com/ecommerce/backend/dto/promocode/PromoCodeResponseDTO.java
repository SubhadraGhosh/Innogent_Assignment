package com.ecommerce.backend.dto.promocode;

import com.ecommerce.backend.enums.DiscountType;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodeResponseDTO {

    private Long id;
    private String code;
    private DiscountType discountType;
    private Double discountValue;
    private LocalDateTime expiryDate;
    private boolean active;
}
