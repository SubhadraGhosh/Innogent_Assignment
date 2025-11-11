package com.ecommerce.backend.entity;

import com.ecommerce.backend.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order extends BaseEntity {

    private Long userId; // Guest or future user reference

    @Column(nullable = false)
    private BigDecimal totalAmount;

    private BigDecimal discountApplied;

    @Column(name = "total_after_discount", precision = 10, scale = 2)
    private BigDecimal totalAfterDiscount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PLACED;

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime deliveryDate;

    private String promoCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();
}
