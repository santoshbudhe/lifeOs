package com.lifeos.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChainFoodService {

    private static final Map<String, Integer> CHAIN_DB = Map.of(
            "mc aloo tikki", 300,
            "mcchicken", 400,
            "zinger burger", 480
    );

    public Integer getCalories(String text) {

        String lower = text.toLowerCase();

        for (String key : CHAIN_DB.keySet()) {
            if (lower.contains(key)) {
                return CHAIN_DB.get(key);
            }
        }

        return null;
    }
}
