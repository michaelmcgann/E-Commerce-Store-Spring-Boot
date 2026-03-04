package com.sbeccommerce.ecommercestore.feature.order.service;

import com.sbeccommerce.ecommercestore.feature.order.DTO.OrderDTO;
import com.sbeccommerce.ecommercestore.feature.order.DTO.OrderRequestDTO;

public interface OrderService {

    OrderDTO placeOrder(String paymentMethod, OrderRequestDTO request);

}
