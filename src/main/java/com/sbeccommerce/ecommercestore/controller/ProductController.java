package com.sbeccommerce.ecommercestore.controller;

import com.sbeccommerce.ecommercestore.DTO.product.ProductDTO;
import com.sbeccommerce.ecommercestore.DTO.product.ProductResponse;
import com.sbeccommerce.ecommercestore.service.ProductService;
import jakarta.validation.Valid;
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
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId) {

        ProductDTO savedProduct = productService.addProduct(categoryId, productDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {

        ProductResponse productResponse = productService.getAllProducts();
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.searchByCategory(categoryId);
        return  ResponseEntity.ok(productResponse);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId,
                                                    @Valid @RequestBody ProductDTO productDTO) {

        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);
        return ResponseEntity.ok(updatedProductDTO);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId) {
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return ResponseEntity.ok(deletedProduct);
    }

}
