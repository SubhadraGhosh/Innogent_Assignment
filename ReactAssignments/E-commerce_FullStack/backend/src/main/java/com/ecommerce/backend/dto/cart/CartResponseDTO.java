package com.ecommerce.backend.dto.cart;

import com.ecommerce.backend.dto.cartitem.CartItemResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class CartResponseDTO {

    private Long cartId;
    private Long userId;
    private BigDecimal totalAmount;
    private List<CartItemResponseDTO> items;
}
