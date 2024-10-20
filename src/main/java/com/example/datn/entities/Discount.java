package com.example.datn.entities;

import lombok.Data;

@Data
public class Discount {
    private Long id;
    private String code;
    private Integer percentage;
    private Double amount;
}
