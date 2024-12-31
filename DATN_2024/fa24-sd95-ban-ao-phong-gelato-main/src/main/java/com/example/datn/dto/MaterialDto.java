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
public class MaterialDto {

    private Integer id;

    @NotEmpty(message = "Mã chất liệu không được để trống")
    @Size(min = 2, max = 10, message = "Mã chất liệu phải từ 2 đến 10 ký tự")
    private String code;

    @NotEmpty(message = "Tên chất liệu không được để trống")
    private String name;
}
