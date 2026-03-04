package com.sbeccommerce.ecommercestore.feature.order.controller;

import com.sbeccommerce.ecommercestore.feature.order.DTO.OrderDTO;
import com.sbeccommerce.ecommercestore.feature.order.DTO.OrderRequestDTO;
import com.sbeccommerce.ecommercestore.feature.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/order/users/payments/{paymentMethod}")
    public ResponseEntity<OrderDTO> orderProducts(@PathVariable String paymentMethod, @RequestBody OrderRequestDTO request) {
        OrderDTO response = orderService.placeOrder(paymentMethod, request);
        return ResponseEntity.ok(response);
    }

}
