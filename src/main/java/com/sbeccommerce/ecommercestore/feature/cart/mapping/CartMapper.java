package com.sbeccommerce.ecommercestore.feature.cart.mapping;

import com.sbeccommerce.ecommercestore.feature.cart.DTO.CartDTO;
import com.sbeccommerce.ecommercestore.feature.cart.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR, uses = CartItemMapper.class)
public interface CartMapper {

    CartDTO toDTO(Cart cart);

    List<CartDTO> toDTOs(List<Cart> carts);

}
