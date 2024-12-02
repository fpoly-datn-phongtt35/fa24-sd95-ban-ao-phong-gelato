package com.example.sd18310.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name ="ctsp")
@AllArgsConstructor
@NoArgsConstructor
public class CTSP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    private Integer id_sp;
//    private Integer id_mau_sac;
//    private Integer id_size;
    private Double gia_ban;
    private int so_luong_ton;
    private String trang_thai;
    private Date ngay_tao;
    private Date ngay_sua;

    @ManyToOne
    @JoinColumn(name = "id_sp")
    private SanPham sanPham;
    @ManyToOne

    @JoinColumn(name = "id_mau_sac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "id_size")
    private Size size;
}
