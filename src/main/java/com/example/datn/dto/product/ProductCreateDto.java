package com.example.datn.dto.product;


import com.example.datn.entities.Brand;
import com.example.datn.entities.Material;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductCreateDto {

    private Integer id;

    @NotBlank(message = "Mã sản phẩm không được bỏ trống !")
    @Size(min = 2, max = 20, message = "Mã sản phẩm phải từ 2 đến 20 ký tự")
    private String code;

    @NotBlank(message = "Tên sản phẩm không được bỏ trống !")
    private String name;

    @NotNull(message = "Vui lòng chọn thương hiệu !")
    private Brand brand;

    @NotNull(message = "Vui lòng chọn chất liệu !")
    private Material material;

    @NotNull(message = "Vui lòng chọn giới tính !")
    private Integer gender;

    private String describe;

    @NotNull(message = "Giá không được bỏ trống !")
    @Min(value = 1000, message = "Giá không được nhỏ hơn 1.000đ !")
    private Double price;
}
