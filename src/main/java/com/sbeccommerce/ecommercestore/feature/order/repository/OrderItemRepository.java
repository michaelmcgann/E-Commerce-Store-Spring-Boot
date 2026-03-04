package com.sbeccommerce.ecommercestore.feature.order.repository;

import com.sbeccommerce.ecommercestore.feature.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
