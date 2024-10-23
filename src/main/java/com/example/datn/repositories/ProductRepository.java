package com.example.datn.repositories;


import com.example.datn.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Product findByCode(String code);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Page<Product> getNameProduct(String name, Pageable pageable);

    boolean existsByCode(String code);

    Product findTopByOrderByIdDesc();
}
