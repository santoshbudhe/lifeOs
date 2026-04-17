package com.logyourmeals.service;

import java.util.UUID;

public interface ActivityService {

    int logActivity(UUID userId, String activityText);

    int getTodayActivityCalories(UUID userId);
}
