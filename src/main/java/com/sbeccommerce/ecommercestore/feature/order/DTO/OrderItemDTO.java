package com.sbeccommerce.ecommercestore.feature.order.DTO;

import com.sbeccommerce.ecommercestore.feature.product.DTO.ProductDTO;

public class OrderItemDTO {

    private Long orderItemId;
    private ProductDTO product;
    private Integer quantity;
    private Double orderItemPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long orderItemId, ProductDTO product, Integer quantity, Double orderItemPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.quantity = quantity;
        this.orderItemPrice = orderItemPrice;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(Double orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }

}
