package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dao.OrderItemDao;
import com.ecommerce.backend.dto.orderitem.OrderItemDTO;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.entity.OrderItem;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.mapper.OrderItemMapper;
import com.ecommerce.backend.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemDao orderItemDao;

    // create - save new order item
    @Override
    @Transactional
    public void saveOrderItem(OrderItemDTO dto, Product product, Order order) {
        // convert dto → entity
        OrderItem orderItem = OrderItemMapper.toEntity(dto, product);

        // set order reference
        orderItem.setOrder(order);

        // save to db
        orderItemDao.save(orderItem);
    }
}
