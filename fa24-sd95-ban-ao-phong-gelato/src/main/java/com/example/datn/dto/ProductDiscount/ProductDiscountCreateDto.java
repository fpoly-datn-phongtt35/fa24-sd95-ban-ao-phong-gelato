package com.example.datn.dto.ProductDiscount;

import lombok.Data;

import java.util.List;

@Data
public class ProductDiscountCreateDto {
    List<ProductDiscountDto> productDiscounts;
}
