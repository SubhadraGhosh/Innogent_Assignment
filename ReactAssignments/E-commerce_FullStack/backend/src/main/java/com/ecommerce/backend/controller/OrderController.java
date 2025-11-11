package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.order.OrderRequestDTO;
import com.ecommerce.backend.dto.order.OrderRequestFrontendDTO;
import com.ecommerce.backend.dto.order.OrderResponseDTO;
import com.ecommerce.backend.dto.order.OrderSummaryDTO;
import com.ecommerce.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // create - place new order
    @PostMapping
    public ResponseEntity<OrderResponseDTO> placeOrder(@RequestBody OrderRequestFrontendDTO frontendDTO) {
        OrderResponseDTO response = orderService.placeOrder(frontendDTO);
        return ResponseEntity.ok(response);
    }

    // checking - get all orders by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUser(@PathVariable Long userId) {
        List<OrderResponseDTO> response = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(response);
    }

    // update - cancel order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        String message = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(message);
    }

    // checking - get summarized orders by user
//    @GetMapping("/user/{userId}/summary")
//    public ResponseEntity<List<OrderResponseDTO>> getOrderSummaryByUser(@PathVariable Long userId) {
//        List<OrderResponseDTO> response = orderService.getOrdersByUser(userId);
//        return ResponseEntity.ok(response);
//    }
    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<List<OrderSummaryDTO>> getOrderSummaryByUser(@PathVariable Long userId) {
        List<OrderSummaryDTO> response = orderService.getOrderSummaryByUser(userId);
        return ResponseEntity.ok(response);
    }
}
