package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dao.OrderDao;
import com.ecommerce.backend.dto.order.*;
import com.ecommerce.backend.dto.orderitem.OrderItemDTO;
import com.ecommerce.backend.entity.*;
import com.ecommerce.backend.enums.DiscountType;
import com.ecommerce.backend.enums.OrderStatus;
import com.ecommerce.backend.mapper.OrderMapper;
import com.ecommerce.backend.repository.AddressRepository;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.service.OrderItemService;
import com.ecommerce.backend.service.OrderService;
import com.ecommerce.backend.service.PromoCodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ProductRepository productRepository;
    private final OrderItemService orderItemService;
    private final PromoCodeService promoCodeService;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public OrderResponseDTO placeOrder(OrderRequestFrontendDTO frontendDTO) {

        OrderRequestDTO requestDTO = OrderMapper.toOrderRequestDTO(frontendDTO, addressRepository);

        List<OrderItemDTO> items = requestDTO.getItems();
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("No items found in order request");
        }

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;

        // calculate total
        for (OrderItemDTO item : items) {
            Long productId = item.getProductId();
            if (productId == null) {
                log.warn("Missing productId for itemId={}, skipping item.", item.getItemId());
                continue;
            }

            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

            if (product.getStock() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getTitle());
            }

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()))
                    .setScale(2, RoundingMode.HALF_UP);

            total = total.add(itemTotal);
        }

        // apply promo
        if (requestDTO.getPromoCode() != null && !requestDTO.getPromoCode().isEmpty()) {
            try {
                var promoResponse = promoCodeService.validatePromo(requestDTO.getPromoCode());

                if (promoResponse.getDiscountType() == DiscountType.PERCENTAGE) {
                    BigDecimal percent = BigDecimal.valueOf(promoResponse.getDiscountValue())
                            .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
                    discount = total.multiply(percent).setScale(2, RoundingMode.HALF_UP);
                } else if (promoResponse.getDiscountType() == DiscountType.FIXED) {
                    discount = BigDecimal.valueOf(promoResponse.getDiscountValue())
                            .setScale(2, RoundingMode.HALF_UP);
                }

                // clamp discount if it exceeds total
                if (discount.compareTo(total) > 0) {
                    log.warn("Discount {} greater than total {}. Adjusting discount to total.", discount, total);
                    discount = total;
                }

            } catch (Exception e) {
                log.warn("Invalid promo code: {}", e.getMessage());
                discount = BigDecimal.ZERO;
            }
        }

        // calculate totalAfterDiscount
        BigDecimal totalAfterDiscount = total.subtract(discount);
        if (totalAfterDiscount.compareTo(BigDecimal.ZERO) < 0) {
            log.warn("Total after discount negative ({}). Setting to 0.", totalAfterDiscount);
            totalAfterDiscount = BigDecimal.ZERO;
        }
        totalAfterDiscount = totalAfterDiscount.setScale(2, RoundingMode.HALF_UP);
        discount = discount.setScale(2, RoundingMode.HALF_UP);

        // fetch address
        Address address = addressRepository.findById(requestDTO.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found with id: " + requestDTO.getAddressId()));

        // build order
        Order order = Order.builder()
                .userId(requestDTO.getUserId())
                .address(address)
                .status(OrderStatus.PLACED)
                .totalAmount(total)
                .discountApplied(discount)
                .totalAfterDiscount(totalAfterDiscount)
                .orderDate(LocalDateTime.now())
                .promoCode(requestDTO.getPromoCode())
                .build();

        Order savedOrder = orderDao.save(order);

        // save items and update stock
        for (OrderItemDTO dto : items) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found with id: " + dto.getProductId()));

            product.setStock(product.getStock() - dto.getQuantity());
            productRepository.save(product);

            orderItemService.saveOrderItem(dto, product, savedOrder);
        }

        log.info("Order placed | userId={} | total={} | discount={} | totalAfterDiscount={}",
                requestDTO.getUserId(), total, discount, totalAfterDiscount);

        return OrderMapper.toDTO(savedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        List<Order> orders = orderDao.findByUserId(userId);
        return orders.stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public String cancelOrder(Long orderId) {
        Order order = orderDao.findById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }

        if (order.getStatus() != OrderStatus.PLACED) {
            throw new RuntimeException("Order cannot be cancelled now");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderDao.save(order);

        for (OrderItem item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }

        log.info("Order cancelled with id={}", orderId);
        return "Order cancelled successfully";
    }

    @Scheduled(fixedRate = 300000)
    @Transactional
    public void autoDeliverOrders() {
        List<Order> placedOrders = orderDao.findByStatus(OrderStatus.PLACED);
        for (Order order : placedOrders) {
            if (order.getOrderDate().isBefore(LocalDateTime.now().minusHours(6))) {
                order.setStatus(OrderStatus.DELIVERED);
                order.setDeliveryDate(LocalDateTime.now());
                orderDao.save(order);
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderSummaryDTO> getOrderSummaryByUser(Long userId) {
        return orderDao.findByUserId(userId)
                .stream()
                .map(OrderMapper::toSummaryDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailsDTO getOrderDetails(Long orderId) {
        Order order = orderDao.findById(orderId);
        if (order == null) {
            throw new RuntimeException("Order not found with id: " + orderId);
        }
        return OrderMapper.toDetailsDTO(order);
    }
}
