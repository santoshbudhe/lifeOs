package com.lifeos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.lifeos.service.ActivityService;

import java.util.UUID;

@RestController
@RequestMapping("/api/activity")
@RequiredArgsConstructor
public class ActivityController {

    private final ActivityService activityService;

    @PostMapping
    public String logActivity(@RequestParam UUID userId,
                             @RequestParam String text) {

        int calories = activityService.logActivity(userId, text);

        return "Activity logged. Burned ~" + calories + " kcal";
    }
}
