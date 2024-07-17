package org.productmanager.controllers;

import org.productmanager.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.productmanager.services.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:8080")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryService.getAllCategories(pageable);
    }

    @GetMapping("/all")
    public List<Category> getAllCategoriesWithoutPagination() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/tree")
    public List<Category> getCategoryTree() {
        return categoryService.getCategoryTreeInorder();
    }

    @GetMapping("/search")
    public Page<Category> searchCategories(@RequestParam String name, Pageable pageable) {
        return categoryService.searchCategories(name, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        Category updatedCategory = categoryService.updateCategory(id, categoryDetails);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}