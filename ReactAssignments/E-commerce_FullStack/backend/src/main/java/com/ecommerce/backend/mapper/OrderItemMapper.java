package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.orderitem.OrderItemDTO;
import com.ecommerce.backend.entity.OrderItem;
import com.ecommerce.backend.entity.Product;

public class OrderItemMapper {

    // Convert DTO → Entity
    public static OrderItem toEntity(OrderItemDTO dto, Product product) {
        return OrderItem.builder()
                .product(product)
                .quantity(dto.getQuantity())
                .price(dto.getPrice())
                .build();
    }

    // Convert Entity → DTO (for responses)
    public static OrderItemDTO toDTO(OrderItem entity) {
        return OrderItemDTO.builder()
                .productId(entity.getProduct().getId())
                .quantity(entity.getQuantity())
                .price(entity.getPrice())
                .build();
    }
}
