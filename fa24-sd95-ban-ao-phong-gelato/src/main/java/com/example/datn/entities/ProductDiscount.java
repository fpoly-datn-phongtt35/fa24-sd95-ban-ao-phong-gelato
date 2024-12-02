package com.example.datn.entities;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter

@Entity
@Table(name = "product_discount")
public class ProductDiscount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double discountedAmount;
    private Date startDate;
    private Date endDate;
    private boolean closed;

    @OneToOne
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}
