package com.example.datn.repositories;


import com.example.datn.entities.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<VerificationCode, Long> {
    VerificationCode findByCode(String code);
}
