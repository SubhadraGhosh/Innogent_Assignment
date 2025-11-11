package com.ecommerce.backend.service;


import com.ecommerce.backend.dto.cart.CartResponseDTO;
import com.ecommerce.backend.dto.cartitem.CartItemRequestDTO;
import jakarta.transaction.Transactional;

public interface CartService {
    @Transactional
    CartResponseDTO addItemToCart(Long userId, CartItemRequestDTO request);

    @Transactional
    CartResponseDTO updateItemQuantity(Long userId, Long itemId, Integer quantity);

    @Transactional
    CartResponseDTO removeItemFromCart(Long userId, Long itemId);

    CartResponseDTO clearCart(Long userId);

    CartResponseDTO getCartDetails(Long userId);
}
