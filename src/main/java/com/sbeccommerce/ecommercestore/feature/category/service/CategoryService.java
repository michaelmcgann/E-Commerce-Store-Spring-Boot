package com.sbeccommerce.ecommercestore.feature.category.service;

import com.sbeccommerce.ecommercestore.feature.category.DTO.CategoryDTO;
import com.sbeccommerce.ecommercestore.feature.category.DTO.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(CategoryDTO category, Long id);
}
