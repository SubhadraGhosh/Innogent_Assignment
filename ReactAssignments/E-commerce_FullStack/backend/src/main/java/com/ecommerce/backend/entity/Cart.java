package com.ecommerce.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "carts")
public class Cart extends BaseEntity {

    // Reference to user (for now static or guest user)
    @Column(name = "user_id")
    private Long userId;

    // Total price of all items in the cart
    @Column(name = "total_amount")
    private BigDecimal totalAmount = BigDecimal.ZERO;

    // One cart -> many cart items
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

}
