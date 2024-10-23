package com.example.datn.dto.product;


import com.example.datn.entities.Color;
import com.example.datn.entities.Size;
import lombok.Data;

@Data
public class ProductDetailDto {

    private Integer id;

    private Integer quantity;

    private String barcode;

    private double price;

    private Integer status;

    private Integer productId;

    private Size size;

    private Color color;
}
