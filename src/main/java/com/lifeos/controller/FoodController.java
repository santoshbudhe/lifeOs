package com.lifeos.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.lifeos.dto.FoodLogResponse;
import com.lifeos.service.FoodLogService;

import java.util.UUID;


@RestController
@RequestMapping("/api/food")
@RequiredArgsConstructor
public class FoodController {


    private final FoodLogService foodLogService;


    @PostMapping("/log")
    public FoodLogResponse logFood(@RequestParam UUID userId,
                                  @RequestParam String text) {


        return foodLogService.logFood(userId, text);
    }
}
