package com.example.datn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "color")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Color implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private Boolean deleteFlag;
    //boolean (kiểu nguyên thủy): Chỉ có hai giá trị khả thi là true hoặc false. Kiểu dữ liệu này không thể nhận giá trị null.
    //Boolean (wrapper class): Có thể nhận ba giá trị: true, false, hoặc null
}
