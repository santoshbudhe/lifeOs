package com.lifeos.service;

import com.lifeos.dto.BehaviorInsight;
import com.lifeos.entity.FoodLog;
import com.lifeos.entity.User;

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

