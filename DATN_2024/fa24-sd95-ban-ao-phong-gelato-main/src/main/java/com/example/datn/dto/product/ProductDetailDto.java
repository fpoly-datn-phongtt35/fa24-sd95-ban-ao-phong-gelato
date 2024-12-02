package com.example.datn.dto.product;


import com.example.datn.entities.Color;
import com.example.datn.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {

    private Integer id;

    private Integer quantity;

    private String barcode;

    private Integer status;

    private Integer productId;

    private Color color;

    private Size size;

    private Double price;
}
