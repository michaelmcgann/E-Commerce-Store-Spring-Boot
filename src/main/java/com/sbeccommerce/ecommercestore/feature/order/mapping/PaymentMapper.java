package com.sbeccommerce.ecommercestore.feature.order.mapping;

import com.sbeccommerce.ecommercestore.feature.order.DTO.PaymentDTO;
import com.sbeccommerce.ecommercestore.feature.order.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PaymentMapper {

    PaymentDTO toPaymentDTO(Payment payment);

}
