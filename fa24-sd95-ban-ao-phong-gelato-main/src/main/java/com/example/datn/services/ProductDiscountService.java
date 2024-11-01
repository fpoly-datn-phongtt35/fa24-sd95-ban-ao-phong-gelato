package com.example.datn.services;



import com.example.datn.dto.ProductDiscount.ProductDiscountCreateDto;
import com.example.datn.dto.ProductDiscount.ProductDiscountDto;
import com.example.datn.entities.ProductDiscount;

import java.util.List;

public interface ProductDiscountService {
    List<ProductDiscount> getAllProductDiscount();

    ProductDiscountDto updateCloseProductDiscount(Long discountId, boolean closed);

    List<ProductDiscountDto> createProductDiscountMultiple(ProductDiscountCreateDto productDiscountCreateDto);
}
