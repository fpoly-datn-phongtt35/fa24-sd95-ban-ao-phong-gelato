package com.example.datn.dto.Order;

import lombok.Data;

@Data
public class OrderDetailDto {
    private Integer quantity;
    private Long productDetailId;
}
