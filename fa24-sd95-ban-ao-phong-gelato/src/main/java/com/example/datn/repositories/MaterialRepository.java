package com.example.datn.repositories;

import com.example.datn.entities.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    boolean existsByCode(String code);
    List<Material> findAllByDeleteFlagFalse();

    Page<Material> findAllByDeleteFlagFalse(Pageable pageable);
}