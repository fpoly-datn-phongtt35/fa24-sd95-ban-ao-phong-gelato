package com.example.datn.services.impls;


import com.example.datn.dto.product.ProductCreateDto;
import com.example.datn.dto.product.ProductSearchDto;
import com.example.datn.entities.Product;
import com.example.datn.repositories.ProductRepository;
import com.example.datn.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Page<Product> getProduct(Integer page) {
        Integer pageSize = 6;
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, pageSize, sort);
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<ProductSearchDto> getSearchProductAndPagining(Pageable pageable) {
        return null;
    }

    @Override
    public Product deleteByStatus(Integer id) {
        return null;
    }

    @Override
    public Product createProduct(ProductCreateDto productDto) {
        Product product = new Product();

        product.setId(productDto.getId());
        product.setCode(productDto.getCode());
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setMaterial(productDto.getMaterial());
        product.setGender(productDto.getGender());
        product.setDescribe(productDto.getDescribe());
        product.setCreate_date(LocalDateTime.now());
        product.setStatus(1);
        product.setDelete_flag(false);

        return productRepository.save(product);
    }

    @Override
    public boolean uniqueCode(String code) {
        return productRepository.existsByCode(code).getDelete_flag();
    }
}
