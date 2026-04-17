package com.lifeos.service;

public interface ActivityCalorieService {

    int estimateCalories(String activityType, Double distance, Integer duration, Integer weight);
}
