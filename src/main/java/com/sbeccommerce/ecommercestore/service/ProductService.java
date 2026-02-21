package com.sbeccommerce.ecommercestore.service;

import com.sbeccommerce.ecommercestore.DTO.product.ProductDTO;

public interface ProductService {


    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

}
