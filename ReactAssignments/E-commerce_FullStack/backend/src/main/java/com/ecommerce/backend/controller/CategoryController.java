package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.category.CategoryRequestDTO;
import com.ecommerce.backend.dto.category.CategoryResponseDTO;
import com.ecommerce.backend.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //  GET all categories
    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        log.info("Fetching all categories");
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //  GET category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long id) {
        log.info("Fetching category with id: {}", id);
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    //  POST create new category
    @PostMapping
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO dto) {
        log.info("Creating new category: {}", dto.getName());
        CategoryResponseDTO createdCategory = categoryService.createCategory(dto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    //  PUT update category
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryRequestDTO dto) {
        log.info("Updating category with id: {}", id);
        CategoryResponseDTO updatedCategory = categoryService.updateCategory(id, dto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    // DELETE category
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        log.warn("Deleting category with id: {}", id);
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully!", HttpStatus.NO_CONTENT);
    }
}
