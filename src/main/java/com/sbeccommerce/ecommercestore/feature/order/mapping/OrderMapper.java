package com.sbeccommerce.ecommercestore.feature.order.mapping;

import com.sbeccommerce.ecommercestore.feature.order.DTO.OrderDTO;
import com.sbeccommerce.ecommercestore.feature.order.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class, PaymentMapper.class}, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderMapper {

    @Mapping(target = "addressId", source = "address.addressId")
    OrderDTO toOrderDTO(Order order);


}
