package com.lifeos.service;

import java.util.UUID;

public interface ActivityService {

    int logActivity(UUID userId, String activityText);

    int getTodayActivityCalories(UUID userId);
}
