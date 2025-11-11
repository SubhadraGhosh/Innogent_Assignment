package com.ecommerce.backend.scheduler;

import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.enums.OrderStatus;
import com.ecommerce.backend.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class OrderStatusScheduler {

    private final OrderRepository orderRepository;

    public OrderStatusScheduler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Run every 30 minutes (adjust as needed)
    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void autoUpdateDeliveredOrders() {
        log.info("Running scheduled task: Checking for orders to mark as DELIVERED...");

        LocalDateTime now = LocalDateTime.now();
        List<Order> orders = orderRepository.findByStatus(OrderStatus.PLACED);

        orders.forEach(order -> {
            if (order.getOrderDate().plusHours(6).isBefore(now)) {
                order.setStatus(OrderStatus.DELIVERED);
                orderRepository.save(order);
                log.info("Order #{} marked as DELIVERED", order.getId());
            }
        });
    }

}
