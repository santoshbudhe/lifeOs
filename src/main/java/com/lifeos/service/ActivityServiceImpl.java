package com.lifeos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lifeos.entity.ActivityLog;
import com.lifeos.entity.User;
import com.lifeos.repository.ActivityLogRepository;
import com.lifeos.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityLogRepository activityLogRepository;
    private final ActivityCalorieService calorieService;
    private final UserRepository userRepository;

    @Override
    public int logActivity(UUID userId, String activityText) {

        String text = activityText.toLowerCase();

        String type = "walking";
        Double distance = null;
        Integer duration = null;

        if (text.contains("run")) {
            type = "running";
        }

        if (text.contains("km")) {
            String[] parts = text.split(" ");
            for (String p : parts) {
                try {
                    distance = Double.parseDouble(p);
                } catch (Exception ignored) {}
            }
        }

        if (text.contains("min")) {
            String[] parts = text.split(" ");
            for (String p : parts) {
                try {
                    duration = Integer.parseInt(p);
                } catch (Exception ignored) {}
            }
        }

        User user = userRepository.findById(userId).orElse(null);

        Integer weight = user != null ? user.getWeight() : 70;

        int calories = calorieService.estimateCalories(type, distance, duration, weight);

        ActivityLog log = ActivityLog.builder()
                .userId(userId)
                .activityType(type)
                .distance(distance)
                .duration(duration)
                .caloriesBurned(calories)
                .timestamp(LocalDateTime.now())
                .build();

        activityLogRepository.save(log);

        return calories;
    }

    @Override
    public int getTodayActivityCalories(UUID userId) {

        LocalDateTime start = LocalDate.now().atStartOfDay();
        LocalDateTime end = LocalDate.now().atTime(23, 59, 59);

        List<ActivityLog> logs =
                activityLogRepository.findByUserIdAndTimestampBetween(userId, start, end);

        return logs.stream()
                .mapToInt(ActivityLog::getCaloriesBurned)
                .sum();
    }
}

