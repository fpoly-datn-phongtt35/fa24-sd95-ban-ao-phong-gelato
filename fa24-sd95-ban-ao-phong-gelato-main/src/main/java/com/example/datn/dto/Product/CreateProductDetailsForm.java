package com.example.datn.dto.Product;

import com.example.datn.entities.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductDetailsForm {
    private List<ProductDetail> productDetailList;
}
