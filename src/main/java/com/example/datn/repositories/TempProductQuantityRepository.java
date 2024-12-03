package com.example.datn.repositories;

import com.example.datn.entities.TempProductQuantity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TempProductQuantityRepository extends JpaRepository<TempProductQuantity, TempProductQuantity.TempProductQuantityId> {


    List<TempProductQuantity> findAllByIdCreateId(Long createId);

    List<TempProductQuantity> findAllByIdCreateIdGreaterThanEqual(Long expiredTime);
}
