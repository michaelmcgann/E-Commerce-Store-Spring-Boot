package com.sbeccommerce.ecommercestore.feature.product.mapping;

import com.sbeccommerce.ecommercestore.feature.product.DTO.ProductDTO;
import com.sbeccommerce.ecommercestore.feature.product.DTO.ProductResponse;
import com.sbeccommerce.ecommercestore.feature.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    @Mapping(target = "user", ignore = true)
    Product toModel(ProductDTO productDTO);

    @Mapping(target = "categoryId", source = "category.categoryId")
    @Mapping(target = "userId", source = "user.userId")
    ProductDTO toProductDTO(Product product);

    List<ProductDTO> toProductsDTOs(List<Product> products);

    default ProductResponse toResponse(Page<Product> productPage) {
        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(toProductsDTOs(productPage.getContent()));
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage(productPage.isLast());

        return productResponse;
    }

}
