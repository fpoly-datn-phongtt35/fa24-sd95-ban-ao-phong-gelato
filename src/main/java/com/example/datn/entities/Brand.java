package com.example.datn.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "brand")
public class Brand implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @Nationalized
    private String name;
    private int status;
    private Boolean deleteFlag;
    //boolean: Dùng khi bạn chắc chắn giá trị sẽ luôn có (không cần null).
    //Boolean: Dùng khi giá trị có thể không xác định (null là hợp lệ).
    //Khi một lớp (class) "implements Serializable", điều đó có nghĩa là các đối tượng của lớp đó có thể được "chuyển thành một chuỗi byte".
    //Khi đã chuyển thành chuỗi byte, đối tượng có thể:
    //Lưu trữ vào file, cơ sở dữ liệu, session, hoặc cache.
    //Truyền qua mạng để gửi thông tin từ máy này sang máy khác.
}
