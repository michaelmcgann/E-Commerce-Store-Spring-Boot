package com.sbeccommerce.ecommercestore.service;

import com.sbeccommerce.ecommercestore.DTO.category.CategoryDTO;
import com.sbeccommerce.ecommercestore.DTO.category.CategoryResponse;
import com.sbeccommerce.ecommercestore.exception.APIException;
import com.sbeccommerce.ecommercestore.exception.ResourceNotFoundException;
import com.sbeccommerce.ecommercestore.model.Category;
import com.sbeccommerce.ecommercestore.repository.CategoryRepository;
import com.sbeccommerce.ecommercestore.utils.mapping.CategoryMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Set<String> allowed = Set.of("categoryName", "categoryId");
        if (!allowed.contains(sortBy)) throw new APIException("sortBy name: '" + sortBy + "' not valid.");

        Sort sortByAndOrder = "asc".equalsIgnoreCase(sortOrder) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        if (categoryPage.isEmpty()) throw new APIException("There are no categories added yet.");

        return categoryMapper.toResponse(categoryPage);
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category foundCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (foundCategory != null ) throw new APIException("Category with name '" + categoryDTO.getCategoryName() + "' already exists.");

        Category savedCategory = categoryRepository.save(categoryMapper.toModel(categoryDTO));
        return categoryMapper.toRequest(savedCategory);
    }

    @Override
    public CategoryDTO deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));

        categoryRepository.delete(category);

        return categoryMapper.toRequest(category);
    }

    @Override
    @Transactional
    public CategoryDTO updateCategory(CategoryDTO category, Long id) {

        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));

        foundCategory.setCategoryName(category.getCategoryName());

        return categoryMapper.toRequest(categoryRepository.save(foundCategory));
    }


}
