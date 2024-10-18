package com.example.datn.services;

import com.example.datn.dto.product.ProductCreateDto;
import com.example.datn.dto.product.ProductSearchDto;
import com.example.datn.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    Page<Product> getProduct(Integer page);

    Page<ProductSearchDto> getSearchProductAndPagining(Pageable pageable);

    Product deleteByStatus(Integer id);

    Product createProduct(ProductCreateDto productDto);

    boolean uniqueCode(String code);
}
