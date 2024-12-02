package com.example.datn.dto.BillReturn;
import lombok.Data;

@Data
public class ReturnDto {
    private Long productDetailId;

    //Số lượng khách hàng đổi
    private Integer quantityReturn;
}
