package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.cart.CartResponseDTO;
import com.ecommerce.backend.dto.cartitem.CartItemResponseDTO;
import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.CartItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CartMapper {

    public CartItemResponseDTO toCartItemResponseDTO(CartItem item) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setId(item.getId());
        dto.setProductId(item.getProduct().getId());
        dto.setProductName(item.getProduct().getTitle());   // assuming Product entity has getTitle()
        dto.setProductImage(item.getProduct().getImageUrl());  // assuming Product entity has getImage()
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setSubtotal(item.getSubtotal());
        return dto;
    }

    public CartResponseDTO toResponseDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getId());
        dto.setUserId(cart.getUserId());
        dto.setTotalAmount(cart.getTotalAmount());

        List<CartItemResponseDTO> items = cart.getItems() == null ? List.of() :
                cart.getItems().stream()
                        .map(this::toCartItemResponseDTO)
                        .collect(Collectors.toList());
        dto.setItems(items);

        return dto;
    }
}
