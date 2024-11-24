package com.example.datn.repositories;

import com.example.datn.entities.BillReturn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRepository extends JpaRepository<BillReturn, Long> {
}
