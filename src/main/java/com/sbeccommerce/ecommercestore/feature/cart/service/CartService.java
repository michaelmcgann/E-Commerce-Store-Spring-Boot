package com.sbeccommerce.ecommercestore.feature.cart.service;

import com.sbeccommerce.ecommercestore.feature.cart.DTO.AddCartItemRequest;
import com.sbeccommerce.ecommercestore.feature.cart.DTO.CartDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CartService {

    @Transactional
    CartDTO addItem(AddCartItemRequest itemRequest);

    List<CartDTO> getAllCarts();

    CartDTO getCurrentUserCart();

    void removeItem(Long productId);
}
