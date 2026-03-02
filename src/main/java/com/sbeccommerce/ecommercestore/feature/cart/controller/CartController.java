package com.sbeccommerce.ecommercestore.feature.cart.controller;

import com.sbeccommerce.ecommercestore.feature.cart.service.CartService;
import com.sbeccommerce.ecommercestore.feature.cart.DTO.AddCartItemRequest;
import com.sbeccommerce.ecommercestore.feature.cart.DTO.CartDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CartController {

    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/carts/items")
    public ResponseEntity<CartDTO> addProductToCart(@RequestBody @Valid AddCartItemRequest itemRequest) {
        CartDTO cartDTO = cartService.addItem(itemRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDTO);
    }

    @GetMapping("/admin/carts")
    public ResponseEntity<List<CartDTO>> getCarts() {
        List<CartDTO> cartDTOS = cartService.getAllCarts();
        return ResponseEntity.status(HttpStatus.OK).body(cartDTOS);
    }

    @GetMapping("/carts/me")
    public ResponseEntity<CartDTO> getCart() {
        CartDTO cartDTO = cartService.getCurrentUserCart();
        return ResponseEntity.status(HttpStatus.OK).body(cartDTO);
    }

    @DeleteMapping("/carts/me/items/{productId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long productId) {
        cartService.removeItem(productId);
        return ResponseEntity.noContent().build();
    }


}
