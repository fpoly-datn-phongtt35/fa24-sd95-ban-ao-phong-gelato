package com.example.sd18310.repository;

import com.example.sd18310.entity.HDCT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HDCTRepository extends JpaRepository<HDCT,Integer> {
}
