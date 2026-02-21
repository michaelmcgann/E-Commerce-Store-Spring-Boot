package com.sbeccommerce.ecommercestore.repository;

import com.sbeccommerce.ecommercestore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {



}
