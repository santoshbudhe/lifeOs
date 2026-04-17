package com.logyourmeals.service;


import com.logyourmeals.entity.DailySummary;


import java.util.UUID;


public interface DailySummaryService {


    DailySummary updateDailyCalories(UUID userId, int calories);


    DailySummary getTodaySummary(UUID userId);


    int getLastNDaysCalorieBalance(UUID userId, int days);
}
