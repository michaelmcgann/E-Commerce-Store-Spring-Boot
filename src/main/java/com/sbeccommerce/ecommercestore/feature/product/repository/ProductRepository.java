package com.sbeccommerce.ecommercestore.feature.product.repository;

import com.sbeccommerce.ecommercestore.feature.category.model.Category;
import com.sbeccommerce.ecommercestore.feature.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> getAllByCategoryOrderByPriceAsc(Category category, Pageable pageable);

    Page<Product> findProductsByProductNameContainsIgnoreCase(String keyword, Pageable pageable);
}
