package com.sbeccommerce.ecommercestore.utils.mapping;

import com.sbeccommerce.ecommercestore.DTO.product.ProductDTO;
import com.sbeccommerce.ecommercestore.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    @Mapping(target = "category", ignore = true)
    Product toModel(ProductDTO productDTO);

    ProductDTO toProductDTO(Product product);

}
