package com.example.datn.repositories;


import com.example.datn.entities.Color;
import com.example.datn.entities.Product;
import com.example.datn.entities.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepo extends JpaRepository<Color,Long> {
    @Query(value = "select distinct c from Color c join ProductDetail pd on c.id = pd.color.id where pd.product = :product")
    List<Color> findColorsByProduct(Product product);

    @Query(value = "select distinct c from Color c join ProductDetail pd on c.id = pd.color.id where pd.product = :product and pd.size = :size")
    List<Color> findColorsByProductAndSize(Product product, Size size);

    boolean existsByCode(String code);

    List<Color> findAllByDeleteFlagFalse();
    Page<Color> findAllByDeleteFlagFalse(Pageable pageable);
}
