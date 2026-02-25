package com.sbeccommerce.ecommercestore.feature.category.controller;

import com.sbeccommerce.ecommercestore.feature.category.DTO.CategoryDTO;
import com.sbeccommerce.ecommercestore.feature.category.DTO.CategoryResponse;
import com.sbeccommerce.ecommercestore.global.config.AppConstants;
import com.sbeccommerce.ecommercestore.feature.category.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/api/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
                                                             @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
                                                             @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY) String sortBy,
                                                             @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_CATEGORIES_ORDER) String sortOrder) {

        return new ResponseEntity<>(categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder), HttpStatus.OK);
    }

    @PostMapping("/api/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO category) {

        CategoryDTO categoryResponse = categoryService.createCategory(category);
        return new ResponseEntity<>(categoryResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/delete/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.deleteCategory(id);
        return ResponseEntity.ok(categoryDTO);

    }

    @PutMapping("/api/admin/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO category, @PathVariable Long id) {
        CategoryDTO savedCategory = categoryService.updateCategory(category, id);
        return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }

}
