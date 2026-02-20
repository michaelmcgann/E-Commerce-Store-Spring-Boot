package com.sbeccommerce.ecommercestore.service;

import com.sbeccommerce.ecommercestore.DTO.category.CategoryDTO;
import com.sbeccommerce.ecommercestore.DTO.category.CategoryResponse;
import com.sbeccommerce.ecommercestore.model.Category;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO updateCategory(CategoryDTO category, Long id);
}
