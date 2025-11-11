package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.cart.CartResponseDTO;
import com.ecommerce.backend.dto.cartitem.CartItemRequestDTO;
import com.ecommerce.backend.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ⚡ Temporary (since no auth yet)
    private final Long STATIC_USER_ID = 1L;

    // ➕ Add item to cart
    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(@RequestBody CartItemRequestDTO request) {
        log.info("API CALL: Add to cart");
        CartResponseDTO response = cartService.addItemToCart(STATIC_USER_ID, request);
        return ResponseEntity.ok(response);
    }

    // ✏️ Update quantity
    @PutMapping("/update/{itemId}")
    public ResponseEntity<CartResponseDTO> updateQuantity(
            @PathVariable Long itemId,
            @RequestParam Integer quantity) {
        log.info("API CALL: Update item {} quantity to {}", itemId, quantity);
        CartResponseDTO response = cartService.updateItemQuantity(STATIC_USER_ID, itemId, quantity);
        return ResponseEntity.ok(response);
    }

    // Remove specific item from cart with in-method exception handling
    @DeleteMapping("/remove/{itemId}")
    public ResponseEntity<CartResponseDTO> removeItem(@PathVariable Long itemId) {
        log.info("API CALL: Remove item {} from cart", itemId);
        try {
            CartResponseDTO response = cartService.removeItemFromCart(STATIC_USER_ID, itemId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException ex) {
            // If item not found or any service error, send 404
            return ResponseEntity.status(404).body(null); // You can also return a custom message instead of null
        }
    }


    // 🧹 Clear entire cart
    @DeleteMapping("/clear")
    public ResponseEntity<CartResponseDTO> clearCart() {
        log.info("API CALL: Clear cart");
        CartResponseDTO response = cartService.clearCart(STATIC_USER_ID);
        return ResponseEntity.ok(response);
    }

    // 🧾 Get full cart details
    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart() {
        log.info("API CALL: Get cart details");
        CartResponseDTO response = cartService.getCartDetails(STATIC_USER_ID);
        return ResponseEntity.ok(response);
    }
}
