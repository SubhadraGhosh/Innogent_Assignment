package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dao.CartDao;
import com.ecommerce.backend.dto.cart.CartResponseDTO;
import com.ecommerce.backend.dto.cartitem.CartItemRequestDTO;
import com.ecommerce.backend.entity.Cart;
import com.ecommerce.backend.entity.CartItem;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.mapper.CartMapper;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.service.CartService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    @Transactional
    public CartResponseDTO addItemToCart(Long userId, CartItemRequestDTO request) {
        log.info("Adding product {} to user {}'s cart", request.getProductId(), userId);

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartDao.findCartByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setTotalAmount(BigDecimal.ZERO);
                    return cartDao.saveCart(newCart);
                });

        if (cart.getItems() == null) {
            throw new RuntimeException("Cart items list is not initialized in entity.");
        }

        Optional<CartItem> existingItemOpt = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == request.getProductId())
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            int newQuantity = existingItem.getQuantity() + request.getQuantity();
            existingItem.setQuantity(newQuantity);
            existingItem.setSubtotal(existingItem.getPrice().multiply(BigDecimal.valueOf(newQuantity)));
            cartDao.saveCartItem(existingItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(product.getPrice());
            cartItem.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
            cartDao.saveCartItem(cartItem);
        }

        cart = cartDao.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        updateCartTotal(cart);
        return cartMapper.toResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO updateItemQuantity(Long userId, Long itemId, Integer quantity) {
        log.info("Updating item {} quantity to {} for user {}", itemId, quantity, userId);

        CartItem item = cartDao.findCartItemById(itemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        item.setQuantity(quantity);
        item.setSubtotal(item.getPrice().multiply(BigDecimal.valueOf(quantity)));
        cartDao.saveCartItem(item);

        Cart cart = cartDao.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        updateCartTotal(cart);
        return cartMapper.toResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO removeItemFromCart(Long userId, Long itemId) {
        log.info("Removing item {} from user {}'s cart", itemId, userId);

        Cart cart = cartDao.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        boolean removed = cart.getItems().removeIf(item -> item.getId() == itemId);
        if (!removed) {
            throw new RuntimeException("Cart item not found");
        }

        cartDao.saveCart(cart);
        updateCartTotal(cart);
        return cartMapper.toResponseDTO(cart);
    }

    @Override
    @Transactional
    public CartResponseDTO clearCart(Long userId) {
        log.info("Clearing cart for user {}", userId);

        // Delete all cart items of that user
        cartDao.clearCart(userId);

        // Get or recreate cart
        Cart cart = cartDao.findCartByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setTotalAmount(BigDecimal.ZERO);
                    return cartDao.saveCart(newCart);
                });

        // Reset total amount
        cart.setTotalAmount(BigDecimal.ZERO);
        cartDao.saveCart(cart);

        // Create empty DTO manually (no old items)
        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(cart.getId());
        response.setUserId(userId);
        response.setTotalAmount(BigDecimal.ZERO);
        response.setItems(Collections.emptyList()); //ensures items = []

        return response;
    }


    @Override
    @Transactional
    public CartResponseDTO getCartDetails(Long userId) {
        log.info("Fetching cart details for user {}", userId);

        Cart cart = cartDao.findCartByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        updateCartTotal(cart);
        return cartMapper.toResponseDTO(cart);
    }

    private void updateCartTotal(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;
        if (cart.getItems() != null && !cart.getItems().isEmpty()) {
            for (CartItem item : cart.getItems()) {
                total = total.add(item.getSubtotal());
            }
        }
        cart.setTotalAmount(total);
        cartDao.saveCart(cart);
    }
}
