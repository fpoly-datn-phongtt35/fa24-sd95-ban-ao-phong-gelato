package com.example.datn.services;


import com.example.datn.dto.product.ProductSearchDto;
import com.example.datn.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface ProductService {

    Page<Product> getProduct(Pageable pageable);

    Product add(Product product) throws IOException;

    Page<ProductSearchDto> searchProduct(Pageable pageable);


}
