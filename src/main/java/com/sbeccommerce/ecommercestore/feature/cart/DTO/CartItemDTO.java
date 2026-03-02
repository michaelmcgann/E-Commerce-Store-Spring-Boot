package com.sbeccommerce.ecommercestore.feature.cart.DTO;

public class CartItemDTO {

    private Long cartItemId;
    private Long productId;
    private Integer quantity;
    private Double discount;

    public CartItemDTO(Long cartItemId, Long productId, Integer quantity, Double discount) {
        this.cartItemId = cartItemId;
        this.productId = productId;
        this.quantity = quantity;
        this.discount = discount;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
