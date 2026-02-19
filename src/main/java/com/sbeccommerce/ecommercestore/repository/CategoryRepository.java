package com.sbeccommerce.ecommercestore.repository;

import com.sbeccommerce.ecommercestore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    Category findByCategoryName(String categoryName);
}
