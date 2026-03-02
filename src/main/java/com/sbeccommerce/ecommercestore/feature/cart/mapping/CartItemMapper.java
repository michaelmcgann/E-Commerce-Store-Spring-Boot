package com.sbeccommerce.ecommercestore.feature.cart.mapping;

import com.sbeccommerce.ecommercestore.feature.cart.DTO.CartItemDTO;
import com.sbeccommerce.ecommercestore.feature.cart.model.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CartItemMapper {

    @Mapping(target = "productId", source = "product.productId")
    CartItemDTO toCartItemDto(CartItem cartItem);

}
