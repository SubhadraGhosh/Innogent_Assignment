package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.category.CategoryRequestDTO;
import com.ecommerce.backend.dto.category.CategoryResponseDTO;
import com.ecommerce.backend.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // Entity → ResponseDTO
    public CategoryResponseDTO toResponseDto(Category category) {
        if (category == null) return null;

        return CategoryResponseDTO.builder()
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    // RequestDTO → Entity
    public Category toEntity(CategoryRequestDTO dto) {
        if (dto == null) return null;

        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }

    // Update existing entity
    public void updateEntityFromRequest(CategoryRequestDTO dto, Category category) {
        if (dto.getName() != null) category.setName(dto.getName());
        if (dto.getDescription() != null) category.setDescription(dto.getDescription());
    }
}
