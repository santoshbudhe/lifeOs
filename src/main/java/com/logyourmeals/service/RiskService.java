package com.logyourmeals.service;

import java.util.UUID;

public interface RiskService {

    String evaluateRisk(UUID userId, int totalCalories, int targetCalories, String energyLevel);

}
