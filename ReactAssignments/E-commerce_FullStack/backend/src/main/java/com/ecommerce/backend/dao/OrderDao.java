package com.ecommerce.backend.dao;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.enums.OrderStatus;
import java.util.List;

public interface OrderDao {

    Order save(Order order);

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    Order findById(Long id);
}
