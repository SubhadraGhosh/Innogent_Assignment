package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "cart_items")
public class CartItem extends BaseEntity {

    // Many items belong to one cart
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // Each item is linked to a product
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Quantity of this product
    @Column(nullable = false)
    private Integer quantity = 1;

    // Product price at the time of adding to cart
    @Column(nullable = false)
    private BigDecimal price;

    // Calculated as price * quantity
    @Column(nullable = false)
    private BigDecimal subtotal;
}
