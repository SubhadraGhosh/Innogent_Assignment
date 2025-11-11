package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.orderitem.OrderItemDTO;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.Product;

public interface OrderItemService {

    // create - save new order item
    void saveOrderItem(OrderItemDTO dto, Product product, Order order);
}
