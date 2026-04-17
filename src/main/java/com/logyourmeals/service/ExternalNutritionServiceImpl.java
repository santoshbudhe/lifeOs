package com.logyourmeals.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ExternalNutritionServiceImpl implements ExternalNutritionService {

    private final RestTemplate restTemplate;

    @Value("${nutrition.api.url}")
    private String apiUrl;

    @Value("${nutrition.api.key}")
    private String apiKey;

    @Override
   public Integer estimateCalories(String query) {
    	return 500;
    }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-Api-Key", apiKey);
//
//        HttpEntity<Void> entity = new HttpEntity<>(headers);
//
//        String url = apiUrl + "?query=" + query;
//
//        ResponseEntity<List<Map<String, Object>>> response =
//                restTemplate.exchange(
//                        url,
//                        HttpMethod.GET,
//                        entity,
//                        new ParameterizedTypeReference<>() {}
//                );
//
//        List<Map<String, Object>> body = response.getBody();
//
//        if (body == null || body.isEmpty()) {
//            return null;
//        }
//
//        double totalCalories = 0;
//
//        for (Map<String, Object> item : body) {
//            Object caloriesObj = item.get("calories");
//            if (caloriesObj instanceof Number) {
//                totalCalories += ((Number) caloriesObj).doubleValue();
//            }
//        }
//
//        return (int) totalCalories;
//    }
}