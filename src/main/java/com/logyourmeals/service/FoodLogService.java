package com.logyourmeals.service;


import com.logyourmeals.dto.FoodLogResponse;


import java.util.UUID;


public interface FoodLogService {
	


    FoodLogResponse logFood(UUID userId, String foodText);
    
}
