package com.ecommerce.backend.dto.order;

import com.ecommerce.backend.enums.OrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {
    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal discountApplied;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private BigDecimal totalAmountAfterDiscount;
    private String promoCode;
}
