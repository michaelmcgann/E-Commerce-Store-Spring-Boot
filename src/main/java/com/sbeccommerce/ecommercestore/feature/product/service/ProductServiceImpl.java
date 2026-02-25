package com.sbeccommerce.ecommercestore.feature.product.service;

import com.sbeccommerce.ecommercestore.feature.product.DTO.ProductDTO;
import com.sbeccommerce.ecommercestore.feature.product.DTO.ProductResponse;
import com.sbeccommerce.ecommercestore.global.common.exception.APIException;
import com.sbeccommerce.ecommercestore.global.common.exception.ResourceNotFoundException;
import com.sbeccommerce.ecommercestore.feature.category.model.Category;
import com.sbeccommerce.ecommercestore.feature.product.model.Product;
import com.sbeccommerce.ecommercestore.feature.category.repository.CategoryRepository;
import com.sbeccommerce.ecommercestore.feature.product.repository.ProductRepository;
import com.sbeccommerce.ecommercestore.feature.product.mapping.ProductMapper;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;

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
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Pageable pageDetails = getPageable(pageNumber, pageSize, sortBy, sortOrder);

        Page<Product> productPage = productRepository.findAll(pageDetails);

        if (productPage.isEmpty()) throw new APIException("There are no products added yet.");

        return productMapper.toResponse(productPage);
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Pageable pageDetails = getPageable(pageNumber, pageSize, sortBy, sortOrder);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));


        Page<Product> productPage = productRepository.getAllByCategoryOrderByPriceAsc(category, pageDetails);
        if (productPage.isEmpty()) throw new APIException("There are no products in this category yet.");

        return productMapper.toResponse(productPage);
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Pageable pageDetails = getPageable(pageNumber, pageSize, sortBy, sortOrder);

        Page<Product> productPage = productRepository.findProductsByProductNameContainsIgnoreCase(keyword, pageDetails);
        if (productPage.isEmpty()) throw new APIException("No products found containing keyword: " + keyword);

        return productMapper.toResponse(productPage);
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


    private static @NonNull Pageable getPageable(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Set<String> allowed = Set.of("productName", "productId");
        if (!allowed.contains(sortBy)) throw new APIException("sortBy name: '" + sortBy + "' not valid.");

        Sort sortByAndOrder = "asc".equalsIgnoreCase(sortOrder) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return PageRequest.of(pageNumber, pageSize, sortByAndOrder);
    }

}
