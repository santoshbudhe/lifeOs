package com.logyourmeals.service;

import com.logyourmeals.entity.User;
import com.logyourmeals.entity.FoodLog;
import com.logyourmeals.dto.BehaviorInsight;

public interface ResponseBuilderService {

    String buildResponse(User user,
                         FoodLog log,
                         BehaviorInsight insight,
                         int totalToday,
                         int target,
                         int last7Days,
                         int netCalories,
                         String nudge, 
                         String energyLevel, 
                         String riskMessage, 
                         String dominantRoutine,
                         String rewardMessage, String goalType);
}

