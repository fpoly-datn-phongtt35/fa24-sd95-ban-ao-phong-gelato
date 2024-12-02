package com.example.datn.dto.Bill;
import com.example.datn.entities.enumClass.BillStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchBillDto {
    private String keyword;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private BillStatus billStatus;
}
