package com.ecommerce.backend.dao.impl;

import com.ecommerce.backend.dao.CartDao;
import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.CartItem;
import com.ecommerce.backend.repository.CartItemRepository;
import com.ecommerce.backend.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CartDaoImpl implements CartDao {

    @Autowired
    private final CartRepository cartRepository;
    @Autowired
    private final CartItemRepository cartItemRepository;

    @Override
    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Optional<Cart> findCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public CartItem saveCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public Optional<CartItem> findCartItemById(Long itemId) {
        return cartItemRepository.findById(itemId);
    }

    @Override
    public void deleteCartItem(Long itemId) {
        cartItemRepository.deleteById(itemId);
    }

    @Override
    public void clearCart(Long userId) {
        cartItemRepository.deleteAllByCart_UserId(userId);
    }
}
