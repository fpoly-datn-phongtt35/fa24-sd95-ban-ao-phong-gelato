package com.example.datn.dto.product;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Integer id;

    private String code;

    private String name;

    private String describe;

    private String imageUrl;

    private Double price;

    private LocalDateTime create_date;

    private LocalDateTime updated_date;
}
