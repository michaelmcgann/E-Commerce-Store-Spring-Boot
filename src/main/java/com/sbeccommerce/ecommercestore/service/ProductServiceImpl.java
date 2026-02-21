package com.sbeccommerce.ecommercestore.service;

import com.sbeccommerce.ecommercestore.DTO.product.ProductDTO;
import com.sbeccommerce.ecommercestore.exception.ResourceNotFoundException;
import com.sbeccommerce.ecommercestore.model.Category;
import com.sbeccommerce.ecommercestore.model.Product;
import com.sbeccommerce.ecommercestore.repository.CategoryRepository;
import com.sbeccommerce.ecommercestore.repository.ProductRepository;
import com.sbeccommerce.ecommercestore.utils.mapping.ProductMapper;
import org.springframework.stereotype.Service;

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
        ProductDTO returnedProductDTO = productMapper.toProductDTO(savedProduct);

        return returnedProductDTO;
    }
}
