package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dao.CategoryDao;
import com.ecommerce.backend.dto.category.CategoryRequestDTO;
import com.ecommerce.backend.dto.category.CategoryResponseDTO;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.mapper.CategoryMapper;
import com.ecommerce.backend.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryDao categoryDao, CategoryMapper categoryMapper) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        log.debug("Retrieving all categories");
        return categoryDao.findAll()
                .stream()
                .map(categoryMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        log.info("Fetching category with id: {}", id);
        Category category = categoryDao.findById(id)
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", id);
                    return new RuntimeException("Category not found");
                });
        return categoryMapper.toResponseDto(category);
    }

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        log.info("Creating new category: {}", dto.getName());
        Category category = categoryMapper.toEntity(dto);
        Category saved = categoryDao.save(category);
        log.debug("Category saved successfully with name: {}", saved.getName());
        return categoryMapper.toResponseDto(saved);
    }

    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto) {
        log.info("Updating category with id: {}", id);
        Category existing = categoryDao.findById(id)
                .orElseThrow(() -> {
                    log.error("Category not found with id: {}", id);
                    return new RuntimeException("Category not found");
                });

        categoryMapper.updateEntityFromRequest(dto, existing);
        Category updated = categoryDao.save(existing);
        log.debug("Category updated successfully with id: {}", updated.getId());
        return categoryMapper.toResponseDto(updated);
    }

    @Override
    public void deleteCategory(Long id) {
        log.warn("Deleting category with id: {}", id);
        categoryDao.deleteById(id);
        log.info("Category deleted successfully with id: {}", id);
    }
}
