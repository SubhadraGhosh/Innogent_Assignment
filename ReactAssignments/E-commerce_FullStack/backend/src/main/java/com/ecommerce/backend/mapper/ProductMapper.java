package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.product.ProductRequestDTO;
import com.ecommerce.backend.dto.product.ProductResponseDTO;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ProductMapper {

    private final CategoryRepository categoryRepository;

    public ProductMapper(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Entity -> ResponseDTO
    public ProductResponseDTO toResponseDto(Product product) {
        if (product == null) return null;

        log.trace("Mapping Product entity to ProductResponseDTO: {}", product.getTitle());

        return ProductResponseDTO.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stock(product.getStock())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .rating(product.getRating())
                .ratingCount(product.getRatingCount())
                .build();
    }

    // RequestDTO -> Entity (create)
    public Product toEntity(ProductRequestDTO dto) {
        if (dto == null) return null;

        log.trace("Mapping ProductRequestDTO to Product entity: {}", dto.getTitle());

        Product product = new Product();
        product.setTitle(dto.getTitle());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setImageUrl(dto.getImageUrl());
        product.setStock(dto.getStock() != null ? dto.getStock() : 0);
        product.setRating(dto.getRating() != null ? dto.getRating() : 0.0);
        product.setRatingCount(dto.getRatingCount() != null ? dto.getRatingCount() : 0);

        // set category: prefer categoryId, then categoryName
        if (dto.getCategoryId() != null) {
            Optional<Category> catOpt = categoryRepository.findById(dto.getCategoryId());
            if (catOpt.isPresent()) {
                product.setCategory(catOpt.get());
                log.debug("Set category by ID '{}' for product '{}'", dto.getCategoryId(), dto.getTitle());
            } else {
                log.warn("Category id '{}' not found for product '{}'", dto.getCategoryId(), dto.getTitle());
            }
        } else if (dto.getCategoryName() != null && !dto.getCategoryName().isBlank()) {
            Category cat = categoryRepository.findByName(dto.getCategoryName());
            if (cat != null) {
                product.setCategory(cat);
                log.debug("Set category by name '{}' for product '{}'", dto.getCategoryName(), dto.getTitle());
            } else {
                log.warn("Category name '{}' not found for product '{}'", dto.getCategoryName(), dto.getTitle());
            }
        }

        return product;
    }

    // Update existing entity from RequestDTO (update)
    public void updateEntityFromRequest(ProductRequestDTO dto, Product product) {
        if (dto == null || product == null) return;

        log.trace("Updating Product entity fields for: {}", product.getTitle());

        if (dto.getTitle() != null) product.setTitle(dto.getTitle());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getImageUrl() != null) product.setImageUrl(dto.getImageUrl());
        if (dto.getStock() != null) product.setStock(dto.getStock());
        if (dto.getRating() != null) product.setRating(dto.getRating());
        if (dto.getRatingCount() != null) product.setRatingCount(dto.getRatingCount());

        // update category: prefer categoryId, then categoryName
        if (dto.getCategoryId() != null) {
            Optional<Category> catOpt = categoryRepository.findById(dto.getCategoryId());
            if (catOpt.isPresent()) {
                product.setCategory(catOpt.get());
                log.debug("Updated category by ID '{}' for product '{}'", dto.getCategoryId(), product.getTitle());
            } else {
                log.warn("Category id '{}' not found during update for '{}'", dto.getCategoryId(), product.getTitle());
            }
        } else if (dto.getCategoryName() != null && !dto.getCategoryName().isBlank()) {
            Category cat = categoryRepository.findByName(dto.getCategoryName());
            if (cat != null) {
                product.setCategory(cat);
                log.debug("Updated category by name '{}' for product '{}'", dto.getCategoryName(), product.getTitle());
            } else {
                log.warn("Category '{}' not found during update for '{}'", dto.getCategoryName(), product.getTitle());
            }
        }
    }
}
