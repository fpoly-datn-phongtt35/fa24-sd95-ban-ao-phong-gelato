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
public class SizeDto {

    private Integer id;

    @NotEmpty(message = "Mã kích cỡ không được để trống")
    @Size(min = 2, max = 10, message = "Mã kích cỡ phải từ 2 đến 10 ký tự")
    private String code;

    @NotEmpty(message = "Tên kích cỡ không được để trống")
    private String name;
}
