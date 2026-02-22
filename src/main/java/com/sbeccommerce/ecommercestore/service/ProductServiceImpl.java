package com.sbeccommerce.ecommercestore.service;

import com.sbeccommerce.ecommercestore.DTO.product.ProductDTO;
import com.sbeccommerce.ecommercestore.DTO.product.ProductResponse;
import com.sbeccommerce.ecommercestore.exception.APIException;
import com.sbeccommerce.ecommercestore.exception.ResourceNotFoundException;
import com.sbeccommerce.ecommercestore.model.Category;
import com.sbeccommerce.ecommercestore.model.Product;
import com.sbeccommerce.ecommercestore.repository.CategoryRepository;
import com.sbeccommerce.ecommercestore.repository.ProductRepository;
import com.sbeccommerce.ecommercestore.utils.mapping.ProductMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Product product = productMapper.toModel(productDTO);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);

        return productMapper.toProductDTO(savedProduct);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) throw new APIException("There are no products added yet.");

        return productMapper.toResponse(products);
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        List<Product> foundProducts = productRepository.getAllByCategoryOrderByPriceAsc(category);
        if (foundProducts.isEmpty()) throw new APIException("There are no products in this category yet.");

        return productMapper.toResponse(foundProducts);
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> foundProducts = productRepository.findProductsByProductNameContainsIgnoreCase(keyword);
        if (foundProducts.isEmpty()) throw new APIException("No products found containing keyword: " + keyword);
        return productMapper.toResponse(foundProducts);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", productDTO.getCategoryId()));

        foundProduct.setCategory(category);
        foundProduct.setProductName(productDTO.getProductName());
        foundProduct.setDescription(productDTO.getDescription());
        foundProduct.setPrice(productDTO.getPrice());
        foundProduct.setQuantity(productDTO.getQuantity());
        foundProduct.setSpecialPrice(productDTO.getSpecialPrice());

        Product updatedProduct = productRepository.save(foundProduct);
        return productMapper.toProductDTO(updatedProduct);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {

        Product foundProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        productRepository.delete(foundProduct);

        return productMapper.toProductDTO(foundProduct);
    }


}
