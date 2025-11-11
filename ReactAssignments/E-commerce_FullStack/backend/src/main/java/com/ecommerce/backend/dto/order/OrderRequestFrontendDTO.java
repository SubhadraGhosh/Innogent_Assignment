package com.ecommerce.backend.dto.order;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequestFrontendDTO {

    // comes from frontend but not persisted
    private Long cartId;

    private Long userId;

    // optional: promoCode or addressId can be added if frontend sends them
    private Long addressId;
    private String promoCode;

    private List<FrontendItemDTO> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FrontendItemDTO {
        private Long id;               // cart item id
        private Long productId;        // actual product id
        private String productName;    // for UI only
        private String productImage;   // for UI only
        private BigDecimal price;      // may differ, backend re-validates
        private Integer quantity;
        private BigDecimal subtotal;   // ignored, backend recalculates
    }
}
