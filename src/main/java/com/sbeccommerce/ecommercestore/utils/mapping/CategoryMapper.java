package com.sbeccommerce.ecommercestore.utils.mapping;

import com.sbeccommerce.ecommercestore.DTO.category.CategoryDTO;
import com.sbeccommerce.ecommercestore.DTO.category.CategoryResponse;
import com.sbeccommerce.ecommercestore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CategoryMapper {

    CategoryDTO toRequest(Category category);

    Category toModel(CategoryDTO categoryDTO);

    List<CategoryDTO> toRequests(List<Category> categories);

    default CategoryResponse toResponse(Page<Category> categoryPage) {

        CategoryResponse response = new CategoryResponse();
        response.setContent(toRequests(categoryPage.getContent()));
        response.setPageNumber(categoryPage.getNumber());
        response.setPageSize(categoryPage.getSize());
        response.setTotalElements(categoryPage.getTotalElements());
        response.setTotalPages(categoryPage.getTotalPages());
        response.setLastPage(categoryPage.isLast());

        return response;
    }

}
