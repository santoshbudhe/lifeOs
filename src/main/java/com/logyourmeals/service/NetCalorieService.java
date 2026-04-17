package com.logyourmeals.service;

import java.util.UUID;

public interface NetCalorieService {

    int calculateNetCalories(UUID userId, int intakeCalories);
}

