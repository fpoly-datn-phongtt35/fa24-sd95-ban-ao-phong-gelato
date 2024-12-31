package com.example.sd18310.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "danh_muc")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ma_danh_muc;

    private String ten_danh_muc;

    private Date ngay_tao;

    private Date ngay_sua;

    private String trang_thai;

}