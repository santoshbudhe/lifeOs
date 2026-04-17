package com.lifeos.service;


import java.util.UUID;

import com.lifeos.entity.DailySummary;


public interface DailySummaryService {


    DailySummary updateDailyCalories(UUID userId, int calories);


    DailySummary getTodaySummary(UUID userId);


    int getLastNDaysCalorieBalance(UUID userId, int days);
}
