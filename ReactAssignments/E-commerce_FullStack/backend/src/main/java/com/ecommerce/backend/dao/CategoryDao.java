package com.ecommerce.backend.dao;

import com.ecommerce.backend.entity.Category;
import java.util.List;
import java.util.Optional;

public interface CategoryDao {

    List<Category> findAll();

    Optional<Category> findById(Long id);

    Category findByName(String name);

    Category save(Category category);

    void deleteById(Long id);
}
