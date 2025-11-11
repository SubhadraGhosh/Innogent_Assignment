package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    //Get all order of a specific user
    List<Order> findByUserId(Long userId);

    // Get orders by status (for scheduler to find pending ones)
    List<Order> findByStatus(OrderStatus status);


}
