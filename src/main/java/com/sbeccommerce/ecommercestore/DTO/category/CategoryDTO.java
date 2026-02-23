package com.sbeccommerce.ecommercestore.DTO.category;

import jakarta.validation.constraints.Size;

public class CategoryDTO {

    private Long categoryId;
    @Size(min = 2, message = "Category name must contain at least 2 characters.")
    private String categoryName;

    public CategoryDTO() {
    }

    public CategoryDTO(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
