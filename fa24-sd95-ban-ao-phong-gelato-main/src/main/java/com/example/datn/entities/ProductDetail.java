package com.example.datn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ProductDetail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;
    private double price;
    private String barcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sizeId")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "colorId")
    private Color color;

    @OneToOne(mappedBy = "productDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductDiscount productDiscount;
}
