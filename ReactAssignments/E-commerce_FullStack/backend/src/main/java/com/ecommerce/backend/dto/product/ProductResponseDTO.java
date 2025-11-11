package com.ecommerce.backend.dto.product;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponseDTO {
    private Long id;
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stock;
    private String categoryName;
    private Double rating;
    private Integer ratingCount;
}
