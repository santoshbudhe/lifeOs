package com.logyourmeals.service;

import org.springframework.stereotype.Service;

@Service
public class ActivityCalorieServiceImpl implements ActivityCalorieService {

    @Override
    public int estimateCalories(String activityType, Double distance, Integer duration, Integer weight) {

        if (weight == null) {
            weight = 70; // fallback
        }

        if ("walking".equalsIgnoreCase(activityType) && distance != null) {
            return (int) (weight * distance * 0.8);
        }

        if ("running".equalsIgnoreCase(activityType) && distance != null) {
            return (int) (weight * distance * 1.0);
        }

        if ("gym".equalsIgnoreCase(activityType) && duration != null) {
            return duration * 5; // rough estimate
        }

        return 100; // fallback
    }
}

