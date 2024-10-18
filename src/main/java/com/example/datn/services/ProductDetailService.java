package com.example.datn.services;

import com.example.datn.dto.product.ProductDetailDto;
import com.example.datn.entities.ProductDetail;

import java.util.List;

public interface ProductDetailService {

    ProductDetail add(ProductDetail productDetail);

    ProductDetail productDetailByProductCode(String code);

    List<ProductDetailDto> getByProductId(Integer id);
}
