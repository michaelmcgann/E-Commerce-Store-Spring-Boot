package com.sbeccommerce.ecommercestore.feature.product.DTO;

import jakarta.validation.constraints.*;

public class ProductDTO {

    private Long productId;

    private Long categoryId;

    @NotBlank(message = "Product name needs to be over 3 characters long")
    @Size(min = 3, message = "Product name needs to be over 3 characters long")
    private String productName;

    @NotBlank(message = "Product description needs to be over 10 characters long")
    @Size(min = 10, message = "Product description needs to be over 10 characters long")
    private String description;

//    private String image;

    @NotNull(message = "Quantity should be zero or more.")
    @PositiveOrZero(message = "Quantity should be zero or more.")
    private Integer quantity;

    @NotNull(message = "Price should be zero or above.")
    @PositiveOrZero(message = "Price should be zero or above.")
    private Double price;

//    private Double discount;

    @NotNull(message = "Price should be zero or above.")
    @PositiveOrZero(message = "Price should be zero or above.")
    private Double specialPrice;

    public ProductDTO() {
    }

    public ProductDTO(Long productId, Long categoryId, String productName, String description, /*String image,*/ Integer quantity, Double price,/* Double discount,*/ Double specialPrice) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.productName = productName;
        this.description = description;
//        this.image = image;
        this.quantity = quantity;
        this.price = price;
//        this.discount = discount;
        this.specialPrice = specialPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

//    public Double getDiscount() {
//        return discount;
//    }
//
//    public void setDiscount(Double discount) {
//        this.discount = discount;
//    }

    public Double getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(Double specialPrice) {
        this.specialPrice = specialPrice;
    }

}
