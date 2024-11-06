package com.example.datn.repositories;

import com.example.datn.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByOrderId(String orderId);
    Payment findByOrderId(String orderId);
}
