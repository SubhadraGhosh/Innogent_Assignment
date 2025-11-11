package com.ecommerce.backend.dao;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ProductDao {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByCategory(Category category);

    Product save(Product product);

    void deleteById(Long id);

    void saveAll(List<Product> products);

}
