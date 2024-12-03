package com.example.datn.dto.Order;


import com.example.datn.dto.CustomerDto.CustomerDto;
import com.example.datn.entities.enumClass.BillStatus;
import com.example.datn.entities.enumClass.InvoiceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private String billId;
    private CustomerDto customer;
    private InvoiceType invoiceType;
    private BillStatus billStatus;
    private Long paymentMethodId;
    private String billingAddress;
    private double promotionPrice;
    private Long voucherId;
    private String orderId;
    private List<OrderDetailDto> orderDetailDtos;
    private Long createId;
}
