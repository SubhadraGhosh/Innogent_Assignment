package com.ecommerce.backend.dto.promocode;

import com.ecommerce.backend.enums.DiscountType;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCodeRequestDTO {

    @NotBlank(message = "Promo code cannot be blank")
    private String code;

    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @NotNull(message = "Discount value is required")
    private Double discountValue;

    @NotNull(message = "Expiry date is required")
    private LocalDateTime expiryDate;

    private boolean active = true;
}
