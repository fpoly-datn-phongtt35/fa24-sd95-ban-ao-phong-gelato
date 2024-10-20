package com.example.datn.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "product_discount")
public class DiscountProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private boolean closed;
//    private Double discountedAmount;
//
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    private LocalDateTime endDate;
//    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
//    private LocalDateTime startDate;
    private Long id;
    private Double discountedAmount;
    private Date startDate;
    private Date endDate;
    private boolean closed;

    @OneToOne
    @JoinColumn(name = "product_detail_id") // lien ket voi productdetail
    private ProductDetail productDetail;

//    // Getter và Setter cho closed
//    public boolean isClosed() {
//        return closed; // Trả về giá trị boolean
//    }
//
//    public void setClosed(boolean closed) {
//        this.closed = closed; // Đặt giá trị boolean
//    }

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Double discountedAmount;
//    private Date startDate;
//    private Date endDate;
//    private boolean closed;
//
//    @OneToOne
//    @JoinColumn(name = "product_detail_id")
//    private ProductDetail productDetail;

}
