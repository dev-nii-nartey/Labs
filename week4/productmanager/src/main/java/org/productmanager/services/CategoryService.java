package org.productmanager.services;

import org.productmanager.entities.Category;
import org.productmanager.model.CategoryTree;
import org.productmanager.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    private CategoryTree categoryTree = new CategoryTree();

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Page<Category> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public Category createCategory(Category category) {
        Category savedCategory = categoryRepository.save(category);
        categoryTree.insert(savedCategory);
        return savedCategory;
    }

    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category != null) {
            category.setName(categoryDetails.getName());
            Category updatedCategory = categoryRepository.save(category);
            categoryTree.delete(id);
            categoryTree.insert(updatedCategory);
            return updatedCategory;
        }
        return null;
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
        categoryTree.delete(id);
    }

    public List<Category> getCategoryTreeInorder() {
        return categoryTree.inorderTraversal();
    }

    public Page<Category> searchCategories(String name, Pageable pageable) {
        return categoryRepository.findByNameContaining(name, pageable);
    }
}