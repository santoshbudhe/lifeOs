package com.logyourmeals.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalorieEngineServiceImpl implements CalorieEngineService {

    private final BeverageService beverageService;
    private final ChainFoodService chainFoodService;
    private final ExternalNutritionService externalService;
    private final CalorieCacheService cacheService;

    @Override
    public Integer estimateCalories(String text) {

        // 1. Beverage check
        Integer beverageCalories = beverageService.getCalories(text);
        if (beverageCalories != null) {
            return beverageCalories;
        }

        // 2. Chain food check
        Integer chainCalories = chainFoodService.getCalories(text);
        if (chainCalories != null) {
            return chainCalories;
        }

        // 3. Cache check
        Integer cached = cacheService.get(text);
        if (cached != null) {
            return cached;
        }

        // 4. External API
        Integer apiCalories = externalService.estimateCalories(text);

        if (apiCalories == null) {
            return 0;
        }

        // 5. Cache result
        cacheService.put(text, apiCalories);

        return apiCalories;
    }
}
