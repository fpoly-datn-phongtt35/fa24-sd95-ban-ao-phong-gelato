package com.example.datn.repositories;

import com.example.datn.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    boolean existsByCode(String code);
    List<Category> findAllByDeleteFlagFalse();
}
