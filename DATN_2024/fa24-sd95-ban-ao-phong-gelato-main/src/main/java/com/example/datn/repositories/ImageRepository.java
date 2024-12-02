package com.example.datn.repositories;


import com.example.datn.entities.Image;
import com.example.datn.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findByProduct(Product product);

    Image findImageById(Integer id);
}
