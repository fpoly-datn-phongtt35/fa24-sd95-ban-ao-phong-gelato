package com.example.datn.dto.product;


import lombok.Data;

@Data
public class SearchProductDto {

    private String code;
    private Double minPrice;
    private Double maxPrice;
    private String productName;
    private String keyword;
    private String barcode;
    private String gender;
}
