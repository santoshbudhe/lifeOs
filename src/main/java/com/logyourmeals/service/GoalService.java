package com.logyourmeals.service;

import com.logyourmeals.entity.UserGoal;

import java.util.UUID;

public interface GoalService {

    UserGoal getOrCreateGoal(UUID userId);

    boolean isSurplusBad(UUID userId);

    int getTargetCalories(UUID userId);
}
