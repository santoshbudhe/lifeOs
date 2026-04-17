package com.lifeos.service;

import java.util.UUID;

import com.lifeos.entity.UserGoal;

public interface GoalService {

    UserGoal getOrCreateGoal(UUID userId);

    boolean isSurplusBad(UUID userId);

    int getTargetCalories(UUID userId);
}
