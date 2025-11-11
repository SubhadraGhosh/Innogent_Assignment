package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.category.CategoryRequestDTO;
import com.ecommerce.backend.dto.category.CategoryResponseDTO;
import java.util.List;

public interface CategoryService {

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Long id);

    CategoryResponseDTO createCategory(CategoryRequestDTO dto);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);

    void deleteCategory(Long id);
}
