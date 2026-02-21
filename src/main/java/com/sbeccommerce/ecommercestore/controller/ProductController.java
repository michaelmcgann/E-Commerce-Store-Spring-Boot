package com.sbeccommerce.ecommercestore.controller;

import com.sbeccommerce.ecommercestore.DTO.product.ProductDTO;
import com.sbeccommerce.ecommercestore.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId) {

        ProductDTO savedProduct = productService.addProduct(categoryId, productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

}
