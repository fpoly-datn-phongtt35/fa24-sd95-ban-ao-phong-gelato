package com.example.datn.repositories;


import com.example.datn.entities.Cart;
import com.example.datn.entities.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByAccount_Id(Long accountId);
    boolean existsByProductDetail_IdAndAccount_Id(Long productDetailId, Long accountId);
    Cart findByProductDetail_IdAndAccount_Id(Long productDetailId, Long accountId);
    Cart findByProductDetail(ProductDetail productDetail);
    void deleteAllByAccount_Id(Long accountId);

}
