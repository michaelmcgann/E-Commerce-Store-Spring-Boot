package com.sbeccommerce.ecommercestore.feature.category.repository;

import com.sbeccommerce.ecommercestore.feature.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByCategoryName(String categoryName);
}
