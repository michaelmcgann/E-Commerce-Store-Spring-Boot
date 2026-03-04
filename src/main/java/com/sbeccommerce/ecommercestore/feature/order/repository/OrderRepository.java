package com.sbeccommerce.ecommercestore.feature.order.repository;

import com.sbeccommerce.ecommercestore.feature.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
