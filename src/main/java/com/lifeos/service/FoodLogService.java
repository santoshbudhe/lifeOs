package com.lifeos.service;


import java.util.UUID;

import com.lifeos.dto.FoodLogResponse;


public interface FoodLogService {
	


    FoodLogResponse logFood(UUID userId, String foodText);
    
}
