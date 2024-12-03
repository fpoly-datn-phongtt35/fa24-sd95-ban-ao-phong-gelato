package com.example.datn.configs;

import com.example.datn.services.serviceImpl.TempProductQuantityService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private final TempProductQuantityService tempProductQuantityService;

    public ScheduledTasks(TempProductQuantityService tempProductQuantityService) {
        this.tempProductQuantityService = tempProductQuantityService;
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void runTaskEvery1Minutes() {
        tempProductQuantityService.deleteExpried();
        //Hoàn nguyên số lượng
        System.out.println("Hoàn nguyên số lượng sau 10p");
    }
}
