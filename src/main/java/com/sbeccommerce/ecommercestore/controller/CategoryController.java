package com.sbeccommerce.ecommercestore.controller;

import com.sbeccommerce.ecommercestore.model.Category;
import com.sbeccommerce.ecommercestore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
    }

    @PostMapping("/api/admin/categories")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {

        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/delete/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        String message = categoryService.deleteCategory(id);
        return ResponseEntity.ok(message);

    }

    @PutMapping("/api/admin/categories/{id}")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long id) {
        Category savedCategory = categoryService.updateCategory(category, id);
        return new ResponseEntity<>("Category with ID: " + savedCategory.getCategoryId() + " updated", HttpStatus.OK);
    }

}
