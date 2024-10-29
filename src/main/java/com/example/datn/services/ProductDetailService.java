package com.example.datn.services;


import com.example.datn.dto.Product.ProductDetailDto;
import com.example.datn.entities.ProductDetail;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDetailService {
    ProductDetail save(ProductDetail productDetail);

    ProductDetail getProductDetailByProductCode(String code) throws NotFoundException;

    List<ProductDetailDto> getByProductId(Long id) throws com.example.datn.exceptions.NotFoundException;
}
