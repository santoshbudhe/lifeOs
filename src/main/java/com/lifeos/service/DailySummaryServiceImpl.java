package com.lifeos.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lifeos.entity.DailySummary;
import com.lifeos.repository.DailySummaryRepository;

import java.time.LocalDate;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class DailySummaryServiceImpl implements DailySummaryService {


    private final DailySummaryRepository dailySummaryRepository;


    private static final int DEFAULT_TARGET = 2000;


    @Override
    public DailySummary updateDailyCalories(UUID userId, int calories) {


        LocalDate today = LocalDate.now();


        DailySummary summary = dailySummaryRepository
                .findByUserIdAndDate(userId, today)
                .orElseGet(() -> DailySummary.builder()
                        .userId(userId)
                        .date(today)
                        .totalCalories(0)
                        .targetCalories(DEFAULT_TARGET)
                        .calorieDelta(0)
                        .build()
                );


        int newTotal = summary.getTotalCalories() + calories;


        summary.setTotalCalories(newTotal);
        summary.setCalorieDelta(newTotal - summary.getTargetCalories());


        return dailySummaryRepository.save(summary);
    }


    @Override
    public DailySummary getTodaySummary(UUID userId) {


        LocalDate today = LocalDate.now();


        return dailySummaryRepository
                .findByUserIdAndDate(userId, today)
                .orElse(null);
    }
    
    @Override
    public int getLastNDaysCalorieBalance(UUID userId, int days) {


        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(days - 1);


        Integer total = dailySummaryRepository
                .getTotalCalorieDeltaBetweenDates(userId, startDate, endDate);


        return total != null ? total : 0;
    }

}
