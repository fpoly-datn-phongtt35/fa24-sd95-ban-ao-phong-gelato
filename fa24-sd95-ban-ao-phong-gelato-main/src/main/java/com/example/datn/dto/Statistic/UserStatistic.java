package com.example.datn.dto.Statistic;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserStatistic {
    private String month;
    private int userCount;

}
