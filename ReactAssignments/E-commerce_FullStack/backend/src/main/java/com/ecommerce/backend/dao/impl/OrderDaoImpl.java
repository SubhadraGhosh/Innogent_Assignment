package com.ecommerce.backend.dao.impl;

import com.ecommerce.backend.dao.OrderDao;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.enums.OrderStatus;
import com.ecommerce.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OrderDaoImpl implements OrderDao {

    private final OrderRepository orderRepository;

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }
}
