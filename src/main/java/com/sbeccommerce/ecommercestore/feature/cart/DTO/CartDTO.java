package com.sbeccommerce.ecommercestore.feature.cart.DTO;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private Long cartId;
    private List<CartItemDTO> cartItems = new ArrayList<>();
    private Double totalPrice = 0.0;

    public CartDTO() {
    }

    public CartDTO(Long cartId, List<CartItemDTO> cartItems, Double totalPrice) {
        this.cartId = cartId;
        this.cartItems = cartItems;
        this.totalPrice = totalPrice;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
