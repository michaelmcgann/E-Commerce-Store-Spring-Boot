package com.sbeccommerce.ecommercestore.feature.order.mapping;

import com.sbeccommerce.ecommercestore.feature.order.DTO.OrderItemDTO;
import com.sbeccommerce.ecommercestore.feature.order.model.OrderItem;
import com.sbeccommerce.ecommercestore.feature.product.mapping.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class}, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderItemMapper {

    OrderItemDTO toOrderItemDTO(OrderItem orderItem);

    List<OrderItemDTO> toOrderItemDTOs(List<OrderItem> orderItems);

}
