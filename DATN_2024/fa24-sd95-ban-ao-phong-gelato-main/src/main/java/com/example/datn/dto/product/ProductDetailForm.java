package com.example.datn.dto.product;


import com.example.datn.entities.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailForm {

    private List<ProductDetail> productDetailList;
}
