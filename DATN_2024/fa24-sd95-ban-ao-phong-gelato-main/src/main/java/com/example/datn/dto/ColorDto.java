package com.example.datn.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ColorDto {

    private Integer id;

    @NotEmpty(message = "Mã màu sắc không được để trống")
    @Size(min = 2, max = 10, message = "Mã màu sắc phải từ 2 đến 10 ký tự")
    private String code;

    @NotEmpty(message = "Tên màu sắc không được để trống")
    private String name;
}
