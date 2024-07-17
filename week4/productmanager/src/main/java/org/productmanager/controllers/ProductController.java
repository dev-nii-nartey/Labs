package org.productmanager.controllers;

import org.productmanager.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.productmanager.services.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public Page<Product> getAllProducts(Pageable pageable) {
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/all")
    public List<Product> getAllCategoriesWithoutPagination() {
        return productService.getAllCategories();
    }

    @GetMapping("/search")
    public Page<Product> searchProducts(@RequestParam String name, Pageable pageable) {
        return productService.searchProducts(name, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product updatedProduct = productService.updateProduct(id, productDetails);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public Page<Product> getProductsByCategory(@PathVariable Long categoryId, Pageable pageable) {
        return productService.getProductsByCategory(categoryId, pageable);
    }
}