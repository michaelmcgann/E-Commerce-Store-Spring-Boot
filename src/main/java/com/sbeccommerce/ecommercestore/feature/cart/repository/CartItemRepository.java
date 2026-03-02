package com.sbeccommerce.ecommercestore.feature.cart.repository;

import com.sbeccommerce.ecommercestore.feature.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {


}
