package com.example.datn.services;

import com.example.datn.dto.BrandDto;
import com.example.datn.entities.Brand;
import org.springframework.data.domain.Page;

public interface BrandService {

    Page<Brand> getBrand(Integer page);

    Brand create(BrandDto brandDto);

    Brand detail(Integer id);

    Brand update(BrandDto brandDto);
}
