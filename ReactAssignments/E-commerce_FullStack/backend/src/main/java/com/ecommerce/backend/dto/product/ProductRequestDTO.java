package com.ecommerce.backend.dto.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductRequestDTO {
    private String title;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stock;


    @JsonProperty("category_id")
    private Long categoryId;        // prefer this if provided

    private String categoryName;    // fallback

    private Double rating;
    private Integer ratingCount;
}
