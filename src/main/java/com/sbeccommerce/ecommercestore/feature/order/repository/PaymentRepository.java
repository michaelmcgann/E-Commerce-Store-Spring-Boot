package com.sbeccommerce.ecommercestore.feature.order.repository;

import com.sbeccommerce.ecommercestore.feature.order.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
