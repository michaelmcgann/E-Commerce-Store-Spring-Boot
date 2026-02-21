package com.sbeccommerce.ecommercestore.service;

import com.sbeccommerce.ecommercestore.DTO.category.CategoryDTO;
import com.sbeccommerce.ecommercestore.DTO.category.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(CategoryDTO category, Long id);
}
