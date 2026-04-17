package com.logyourmeals.controller;


import com.logyourmeals.dto.FoodLogResponse;
import com.logyourmeals.service.FoodLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


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
