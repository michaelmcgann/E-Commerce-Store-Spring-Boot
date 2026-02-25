package com.sbeccommerce.ecommercestore.feature.product.service;

import com.sbeccommerce.ecommercestore.feature.product.DTO.ProductDTO;
import com.sbeccommerce.ecommercestore.feature.product.DTO.ProductResponse;

public interface ProductService {


    ProductDTO addProduct(Long categoryId, ProductDTO productDTO);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO deleteProduct(Long productId);
}
