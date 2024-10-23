package com.example.datn.services.impls;


import com.example.datn.dto.product.ProductDetailDto;
import com.example.datn.entities.ProductDetail;
import com.example.datn.repositories.ProductDetailRepository;
import com.example.datn.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    private ProductDetailRepository productDetailRepository;


    @Override
    public ProductDetail add(ProductDetailDto productDetailDto) {
       ProductDetail productDetail = new ProductDetail();
       productDetail.setId(productDetailDto.getId());
       productDetail.setColor(productDetailDto.getColor());
       productDetail.setQuantity(productDetailDto.getQuantity());
       productDetail.setPrice(productDetailDto.getPrice());


       return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail productDetailByProductCode(String code) {
        return null;
    }

    @Override
    public List<ProductDetailDto> getByProductId(Integer id) {
        return null;
    }
}
