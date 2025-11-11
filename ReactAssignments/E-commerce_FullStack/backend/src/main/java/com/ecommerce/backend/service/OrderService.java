package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.order.*;

import java.util.List;

public interface OrderService {

    // create - place new order
    OrderResponseDTO placeOrder(OrderRequestFrontendDTO requestDTO);

    // checking - get all orders by user
    List<OrderResponseDTO> getOrdersByUser(Long userId);

    // update - cancel an order
    String cancelOrder(Long orderId);

    // checking - get all order summaries
    List<OrderSummaryDTO> getOrderSummaryByUser(Long userId);

    // checking - get detailed order
    OrderDetailsDTO getOrderDetails(Long orderId);
}
