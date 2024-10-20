package com.example.datn.services;

import com.example.datn.dto.DiscountProduct.DiscountProductCreateDto;
import com.example.datn.dto.DiscountProduct.DiscountProductDto;
import com.example.datn.entities.DiscountProduct;

import java.util.List;

public interface ProductDiscountService {
    List<DiscountProduct> getAllProductDiscount();

    DiscountProductDto updateCloseProductDiscount(Long discountId, boolean closed);

    List<DiscountProductDto> createProductDiscountMultiple(DiscountProductCreateDto discountProductCreateDto);
}
