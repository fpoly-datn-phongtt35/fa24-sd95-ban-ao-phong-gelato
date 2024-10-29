package com.example.datn.controllers.api;


import com.example.datn.dto.Brand.BrandDto;
import com.example.datn.services.BrandService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class BrandRestController {
    private final BrandService brandService;

    public BrandRestController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping("/api/brand")
    public BrandDto createBrandApi(@RequestBody @Valid BrandDto brandDto) {
        return brandService.createBrandApi(brandDto);
    }
}
