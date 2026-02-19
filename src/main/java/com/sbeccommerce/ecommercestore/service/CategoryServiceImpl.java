package com.sbeccommerce.ecommercestore.service;

import com.sbeccommerce.ecommercestore.exception.APIException;
import com.sbeccommerce.ecommercestore.exception.ResourceNotFoundException;
import com.sbeccommerce.ecommercestore.model.Category;
import com.sbeccommerce.ecommercestore.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) throw new APIException("There are no categories added yet.");
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategory != null ) throw new APIException("Category with name '" + category.getCategoryName() + "' already exists.");

        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));

        categoryRepository.delete(category);
        return "Category deleted.";
    }

    @Override
    @Transactional
    public Category updateCategory(Category category, Long id) {

        Category foundCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", id));

        foundCategory.setCategoryName(category.getCategoryName());

        return categoryRepository.save(foundCategory);
    }


}
