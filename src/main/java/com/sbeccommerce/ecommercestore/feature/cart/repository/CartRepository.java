package com.sbeccommerce.ecommercestore.feature.cart.repository;

import com.sbeccommerce.ecommercestore.feature.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findCartByUser_UserId(Long userId);

    Cart findCartByUser_Email(String userEmail);


}
