package com.ecommerce.backend.dao.impl;

import com.ecommerce.backend.dao.OrderItemDao;
import com.ecommerce.backend.entity.OrderItem;
import com.ecommerce.backend.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderItemDaoImpl implements OrderItemDao {

    private final OrderItemRepository orderItemRepository;

    @Override
    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}
