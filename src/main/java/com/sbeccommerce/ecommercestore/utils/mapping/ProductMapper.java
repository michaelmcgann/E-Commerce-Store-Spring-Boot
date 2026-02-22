package com.sbeccommerce.ecommercestore.utils.mapping;

import com.sbeccommerce.ecommercestore.DTO.product.ProductDTO;
import com.sbeccommerce.ecommercestore.DTO.product.ProductResponse;
import com.sbeccommerce.ecommercestore.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    Product toModel(ProductDTO productDTO);

    ProductDTO toProductDTO(Product product);

    List<ProductDTO> toProductsDTOs(List<Product> products);

    default ProductResponse toResponse(List<Product> products) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(toProductsDTOs(products));
        return productResponse;
    }

}
