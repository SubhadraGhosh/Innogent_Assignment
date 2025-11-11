package com.ecommerce.backend.dto.order;

import com.ecommerce.backend.dto.orderitem.OrderItemDTO;
import com.ecommerce.backend.dto.address.AddressResponseDTO;
import com.ecommerce.backend.enums.OrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailsDTO {

    private Long id;
    private BigDecimal totalAmount;
    private BigDecimal discountApplied;
    private BigDecimal totalAmountAfterDiscount;
    private String promoCode;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private AddressResponseDTO address;
    private List<OrderItemDTO> items;
}
