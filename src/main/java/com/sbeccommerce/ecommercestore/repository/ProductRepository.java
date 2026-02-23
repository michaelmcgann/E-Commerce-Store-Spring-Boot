package com.sbeccommerce.ecommercestore.repository;

import com.sbeccommerce.ecommercestore.model.Category;
import com.sbeccommerce.ecommercestore.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> getAllByCategoryOrderByPriceAsc(Category category, Pageable pageable);

    Page<Product> findProductsByProductNameContainsIgnoreCase(String keyword, Pageable pageable);
}
