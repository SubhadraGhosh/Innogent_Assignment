package com.ecommerce.backend.repository;

import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Fetch all products by category
    List<Product> findByCategory(Category category);

    // Optional: search products by title (useful later for filters/search)
    List<Product> findByTitleContainingIgnoreCase(String title);
}
