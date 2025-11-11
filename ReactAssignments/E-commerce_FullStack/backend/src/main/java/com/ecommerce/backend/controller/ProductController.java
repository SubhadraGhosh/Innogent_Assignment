package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.product.ProductRequestDTO;
import com.ecommerce.backend.dto.product.ProductResponseDTO;
import com.ecommerce.backend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET all products (or by category)
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(
            @RequestParam(required = false) String category) {

        if (category != null) {
            log.info("Fetching products by category: {}", category);
            List<ProductResponseDTO> products = productService.getProductsByCategory(category);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }

        log.info("Fetching all products");
        List<ProductResponseDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // GET single product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        log.info("Fetching product with id: {}", id);
        ProductResponseDTO product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    // POST create new product
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO requestDTO) {
        log.info("Creating new product: {}", requestDTO.getTitle());
        ProductResponseDTO createdProduct = productService.createProduct(requestDTO);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // PUT update product
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO requestDTO) {

        log.info("Updating product with id: {}", id);
        ProductResponseDTO updatedProduct = productService.updateProduct(id, requestDTO);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    // DELETE product
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        log.warn("Deleting product with id: {}", id);
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully!", HttpStatus.NO_CONTENT);
    }

    // POST — Add multiple products at once
    @PostMapping("/bulk")
    public ResponseEntity<String> addMultipleProducts(@RequestBody List<ProductRequestDTO> requestDTOs) {
        log.info("Received {} products for bulk insert", requestDTOs.size());
        productService.createProductsInBulk(requestDTOs);
        return new ResponseEntity<>("All products inserted successfully!", HttpStatus.CREATED);
    }

}
