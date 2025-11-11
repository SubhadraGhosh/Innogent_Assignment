package com.ecommerce.backend.dao;

import com.ecommerce.backend.entity.OrderItem;

public interface OrderItemDao {
    OrderItem save(OrderItem orderItem);
}
