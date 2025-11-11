package com.ecommerce.backend.dto.cartitem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDTO {

    private Long productId;
    private Integer quantity = 1;
}
