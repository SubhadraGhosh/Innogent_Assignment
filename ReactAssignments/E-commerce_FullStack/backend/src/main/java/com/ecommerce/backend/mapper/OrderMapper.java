package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.address.AddressResponseDTO;
import com.ecommerce.backend.dto.order.*;
import com.ecommerce.backend.dto.orderitem.OrderItemDTO;
import com.ecommerce.backend.entity.Address;
import com.ecommerce.backend.entity.Order;
import com.ecommerce.backend.repository.AddressRepository;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    // Convert Entity → DTO for returning to frontend
    public static OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .discountApplied(order.getDiscountApplied())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .promoCode(order.getPromoCode())
                .totalAmountAfterDiscount(order.getTotalAmount().subtract(order.getDiscountApplied()))
                .build();
    }

    // create - convert Order → OrderDetailsDTO (full detail view)
    public static OrderDetailsDTO toDetailsDTO(Order order) {
        AddressResponseDTO addressDTO = AddressMapper.toDTO(order.getAddress());

        List<OrderItemDTO> items = order.getOrderItems().stream()
                .map(OrderItemMapper::toDTO)
                .collect(Collectors.toList());

        return OrderDetailsDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .discountApplied(order.getDiscountApplied())
                .totalAmountAfterDiscount(order.getTotalAmount().subtract(order.getDiscountApplied()))
                .promoCode(order.getPromoCode())
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .address(addressDTO)
                .items(items)
                .build();
    }

    // create - convert Order → OrderSummaryDTO (for My Orders)
    public static OrderSummaryDTO toSummaryDTO(Order order) {
        int itemCount = 0;
        try {
            if (order.getOrderItems() != null) {
                itemCount = order.getOrderItems().size();
            }
        } catch (Exception e) {
            itemCount = 0;
        }

        return OrderSummaryDTO.builder()
                .id(order.getId())
                .totalAmount(order.getTotalAmount())
                .discountApplied(order.getDiscountApplied())
                .totalAmountAfterDiscount(order.getTotalAmount().subtract(order.getDiscountApplied()))
                .status(order.getStatus())
                .orderDate(order.getOrderDate())
                .itemCount(itemCount)
                .build();
    }


    // Convert Frontend DTO → Core DTO (for service layer)
    public static OrderRequestDTO toOrderRequestDTO(
            OrderRequestFrontendDTO frontendDTO,
            AddressRepository addressRepository) {

        Address address = null;
        if (frontendDTO.getAddressId() != null) {
            address = addressRepository.findById(frontendDTO.getAddressId())
                    .orElseThrow(() ->
                            new RuntimeException("Address not found with id: " + frontendDTO.getAddressId()));
        }

        return OrderRequestDTO.builder()
                .userId(frontendDTO.getUserId())
                .addressId(frontendDTO.getAddressId())
                .promoCode(frontendDTO.getPromoCode())
                .items(mapFrontendItems(frontendDTO.getItems()))
                .build();
    }

    // Helper: Map frontend items → backend DTOs
    private static List<OrderItemDTO> mapFrontendItems(List<OrderRequestFrontendDTO.FrontendItemDTO> items) {
        if (items == null || items.isEmpty()) return List.of();

        return items.stream()
                .map(item -> OrderItemDTO.builder()
                        .itemId(item.getId())            // cart item id
                        .productId(item.getProductId())  // actual product id
                        .quantity(item.getQuantity())
                        .price(item.getPrice())          // backend recalculates total later
                        .build())
                .collect(Collectors.toList());
    }

}
