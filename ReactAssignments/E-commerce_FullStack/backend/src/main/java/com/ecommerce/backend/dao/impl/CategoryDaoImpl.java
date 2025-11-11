package com.ecommerce.backend.dao.impl;

import com.ecommerce.backend.dao.CategoryDao;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class CategoryDaoImpl implements CategoryDao {

    private final CategoryRepository categoryRepository;

    public CategoryDaoImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        log.debug("Fetching all categories from database");
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> findById(Long id) {
        log.debug("Fetching category with id: {}", id);
        return categoryRepository.findById(id);
    }

    @Override
    public Category findByName(String name) {
        log.debug("Fetching category by name: {}", name);
        return categoryRepository.findByName(name);
    }

    @Override
    public Category save(Category category) {
        log.info("Saving category: {}", category.getName());
        return categoryRepository.save(category);
    }

    @Override
    public void deleteById(Long id) {
        log.warn("Deleting category with id: {}", id);
        categoryRepository.deleteById(id);
    }
}
