package com.ecommerce.backend.entity;

import com.ecommerce.backend.enums.DiscountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "promo_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoCode extends BaseEntity {

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Promo code cannot be blank")
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DiscountType discountType;

    @NotNull(message = "Discount value is required")
    private Double discountValue;

    @NotNull(message = "Expiry date is required")
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private boolean active = true;
}
