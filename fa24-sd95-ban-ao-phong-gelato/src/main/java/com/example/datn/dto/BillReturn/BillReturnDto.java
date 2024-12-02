package com.example.datn.dto.BillReturn;
import com.example.datn.dto.CustomerDto.CustomerDto;
import lombok.Data;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Data
public class BillReturnDto {
    private Long id;

    private String code;

    private String returnReason;

    private CustomerDto customer;

    private LocalDateTime returnDate;

    private Double returnMoney;

    private boolean isCancel;

    private int returnStatus;
}
