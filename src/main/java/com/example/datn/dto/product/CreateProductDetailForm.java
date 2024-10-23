package com.example.datn.dto.product;


import com.example.datn.entities.ProductDetail;
import lombok.Data;

import java.util.List;

@Data
public class CreateProductDetailForm {

    public List<ProductDetail> productDetailList;
}
