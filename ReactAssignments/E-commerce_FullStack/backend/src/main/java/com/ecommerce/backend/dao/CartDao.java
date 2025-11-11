package com.ecommerce.backend.dao;

import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.CartItem;

import java.util.Optional;

public interface CartDao {

    Cart saveCart(Cart cart);

    Optional<Cart> findCartByUserId(Long userId);

    CartItem saveCartItem(CartItem cartItem);

    Optional<CartItem> findCartItemById(Long itemId);

    void deleteCartItem(Long itemId);

    void clearCart(Long userId);
}
