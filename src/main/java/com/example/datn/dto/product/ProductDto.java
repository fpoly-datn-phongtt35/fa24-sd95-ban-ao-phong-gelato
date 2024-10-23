package com.example.datn.dto.product;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDto {

    private Integer id;

    private String code;

    private String name;

    private String describe;

    private String imageUrl;

    private Double price;

    private LocalDateTime create_date;

    private LocalDateTime updated_date;

    private List<ProductDetailDto> productDetailDtos;
}
