package com.sbeccommerce.ecommercestore.repository;

import com.sbeccommerce.ecommercestore.model.Category;
import com.sbeccommerce.ecommercestore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> getAllByCategoryOrderByPriceAsc(Category category);

    List<Product> findProductsByProductNameContainsIgnoreCase(String keyword);
}
