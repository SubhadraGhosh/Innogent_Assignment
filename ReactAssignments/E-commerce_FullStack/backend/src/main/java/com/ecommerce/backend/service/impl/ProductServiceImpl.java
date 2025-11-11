package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.dao.ProductDao;
import com.ecommerce.backend.dto.product.ProductRequestDTO;
import com.ecommerce.backend.dto.product.ProductResponseDTO;
import com.ecommerce.backend.entity.Category;
import com.ecommerce.backend.entity.Product;
import com.ecommerce.backend.mapper.ProductMapper;
import com.ecommerce.backend.repository.CategoryRepository;
import com.ecommerce.backend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductDao productDao, ProductMapper productMapper, CategoryRepository categoryRepository) {
        this.productDao = productDao;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        log.debug("Retrieving all products");
        return productDao.findAll()
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {
        log.info("Fetching product with id: {}", id);
        Product product = productDao.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new RuntimeException("Product not found");
                });
        return productMapper.toResponseDto(product);
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(String categoryName) {
        log.debug("Fetching products under category: {}", categoryName);
        Category category = categoryRepository.findByName(categoryName);
        if (category == null) {
            log.warn("Category not found: {}", categoryName);
            return List.of();
        }

        return productDao.findByCategory(category)
                .stream()
                .map(productMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        log.info("Creating new product: {}", requestDTO.getTitle());
        Product product = productMapper.toEntity(requestDTO);
        Product savedProduct = productDao.save(product);
        log.debug("Product saved successfully: {}", savedProduct.getTitle());
        return productMapper.toResponseDto(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        log.info("Updating product with id: {}", id);
        Product existingProduct = productDao.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new RuntimeException("Product not found");
                });

        productMapper.updateEntityFromRequest(requestDTO, existingProduct);
        Product updated = productDao.save(existingProduct);
        log.debug("Product updated successfully: {}", updated.getTitle());
        return productMapper.toResponseDto(updated);
    }

    @Override
    public void deleteProduct(Long id) {
        log.warn("Deleting product with id: {}", id);
        productDao.deleteById(id);
        log.info("Product deleted successfully with id: {}", id);
    }

    @Override
    public void createProductsInBulk(List<ProductRequestDTO> requestDTOs) {
        log.info("Inserting {} products into database", requestDTOs.size());

        // Convert DTO list → Entity list
        List<Product> products = requestDTOs.stream()
                .map(productMapper::toEntity)
                .collect(Collectors.toList());

        // Save all at once
        productDao.saveAll(products);

        log.info(" Successfully inserted {} products", products.size());
    }

}
