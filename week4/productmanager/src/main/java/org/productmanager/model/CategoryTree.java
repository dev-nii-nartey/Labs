package org.productmanager.model;

import org.productmanager.entities.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryTree {
    private CategoryNode root;

    private static class CategoryNode {
        Category category;
        CategoryNode left;
        CategoryNode right;

        CategoryNode(Category category) {
            this.category = category;
        }
    }

    public void insert(Category category) {
        root = insertRec(root, category);
    }

    private CategoryNode insertRec(CategoryNode root, Category category) {
        if (root == null) {
            return new CategoryNode(category);
        }

        if (category.getId() < root.category.getId()) {
            root.left = insertRec(root.left, category);
        } else if (category.getId() > root.category.getId()) {
            root.right = insertRec(root.right, category);
        }

        return root;
    }

    public Category search(Long id) {
        return searchRec(root, id);
    }

    private Category searchRec(CategoryNode root, Long id) {
        if (root == null || root.category.getId().equals(id)) {
            return root != null ? root.category : null;
        }

        if (id < root.category.getId()) {
            return searchRec(root.left, id);
        }

        return searchRec(root.right, id);
    }

    public void delete(Long id) {
        root = deleteRec(root, id);
    }

    private CategoryNode deleteRec(CategoryNode root, Long id) {
        if (root == null) {
            return null;
        }

        if (id < root.category.getId()) {
            root.left = deleteRec(root.left, id);
        } else if (id > root.category.getId()) {
            root.right = deleteRec(root.right, id);
        } else {
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            root.category = minValue(root.right);
            root.right = deleteRec(root.right, root.category.getId());
        }

        return root;
    }

    private Category minValue(CategoryNode root) {
        Category minv = root.category;
        while (root.left != null) {
            minv = root.left.category;
            root = root.left;
        }
        return minv;
    }

    public List<Category> inorderTraversal() {
        List<Category> result = new ArrayList<>();
        inorderTraversalRec(root, result);
        return result;
    }

    private void inorderTraversalRec(CategoryNode root, List<Category> result) {
        if (root != null) {
            inorderTraversalRec(root.left, result);
            result.add(root.category);
            inorderTraversalRec(root.right, result);
        }
    }
}