package com.example.datn.repositories;

import com.example.datn.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayMentMethodRepository extends JpaRepository<PaymentMethod, Long> {
}
