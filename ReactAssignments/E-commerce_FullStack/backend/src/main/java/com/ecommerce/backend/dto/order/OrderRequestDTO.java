package com.ecommerce.backend.dto.order;

import com.ecommerce.backend.dto.orderitem.OrderItemDTO;
import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestDTO {
    private Long userId;
    private Long addressId;
    private String promoCode;
    private List<OrderItemDTO> items; // checking
}
