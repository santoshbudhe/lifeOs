package com.lifeos.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BeverageService {

    private static final Map<String, Integer> BEVERAGE_DB = Map.of(
            "coke", 139,
            "coca cola", 139,
            "pepsi", 150,
            "heineken", 142,
            "kingfisher", 218
    );

    public Integer getCalories(String text) {

        String lower = text.toLowerCase();

        for (String key : BEVERAGE_DB.keySet()) {
            if (lower.contains(key)) {
                return BEVERAGE_DB.get(key);
            }
        }

        return null;
    }
}