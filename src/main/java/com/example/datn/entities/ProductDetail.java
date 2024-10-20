package com.example.datn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String barcode;
    private double price;
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    //@JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToOne(mappedBy = "productDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference // Quản lý chuỗi tuần tự hóa JSON
    private DiscountProduct productDiscount;
}
