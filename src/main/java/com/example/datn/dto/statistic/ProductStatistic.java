package com.example.datn.dto.statistic;

public interface ProductStatistic {
    String getCode();
    String getName();
    String getBrand();
    String getCategory();
    int getTotalQuantity();
    int getTotalQuantityReturn();
    Double getRevenue();
}
