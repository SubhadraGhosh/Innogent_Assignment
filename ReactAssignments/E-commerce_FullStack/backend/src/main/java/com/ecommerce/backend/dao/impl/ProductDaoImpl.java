package com.ecommerce.backend.dao.impl;

import com.ecommerce.backend.dao.ProductDao;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class ProductDaoImpl implements ProductDao {

    private final ProductRepository productRepository;

    public ProductDaoImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> findAll() {
        log.debug("Fetching all products from database");
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(Long id) {
        log.debug("Fetching product with id: {}", id);
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findByCategory(Category category) {
        log.debug("Fetching products by category: {}", category.getName());
        return productRepository.findByCategory(category);
    }

    @Override
    public Product save(Product product) {
        log.info("Saving product: {}", product.getTitle());
        return productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        log.warn("Deleting product with id: {}", id);
        productRepository.deleteById(id);
    }

    @Override
    public void saveAll(List<Product> products) {
        log.info("Bulk saving {} products", products.size());
        productRepository.saveAll(products);
    }

}
