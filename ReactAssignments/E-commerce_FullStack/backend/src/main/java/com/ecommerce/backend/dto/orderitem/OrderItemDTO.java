package com.ecommerce.backend.dto.orderitem;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {
    @JsonAlias("id") // ensures frontend "id" maps to "itemId"
    private Long itemId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
}
