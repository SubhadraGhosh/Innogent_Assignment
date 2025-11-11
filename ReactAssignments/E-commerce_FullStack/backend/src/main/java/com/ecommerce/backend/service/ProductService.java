package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.product.ProductRequestDTO;
import com.ecommerce.backend.dto.product.ProductResponseDTO;
import java.util.List;

public interface ProductService {

    List<ProductResponseDTO> getAllProducts();

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getProductsByCategory(String categoryName);

    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO);

    void deleteProduct(Long id);

    void createProductsInBulk(List<ProductRequestDTO> requestDTOs);
}
