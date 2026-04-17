package com.lifeos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lifeos.entity.FoodLog;
import com.lifeos.entity.User;
import com.lifeos.repository.FoodLogRepository;
import com.lifeos.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserClassificationServiceImpl implements UserClassificationService {

    private final FoodLogRepository foodLogRepository;
    private final UserRepository userRepository;

    @Override
    public void updateUserClassification(UUID userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 Last 3 days data
        LocalDateTime last3Days = LocalDateTime.now().minusDays(3);

        List<FoodLog> logs = foodLogRepository
                .findByUserIdAndTimestampAfter(userId, last3Days);

        if (logs.isEmpty()) {
            return;
        }

        int liquidCaloriesCount = 0;
        int cigaretteCount = 0;
        int lateFirstMealCount = 0;

        for (FoodLog log : logs) {

            String text = log.getFoodText().toLowerCase();

            // 🔥 Detect liquid calories
            if (text.contains("coke") || text.contains("limca") || text.contains("dew")) {
                liquidCaloriesCount++;
            }

            // 🔥 Detect smoking
            if (text.contains("cigarette") || text.contains("smoke")) {
                cigaretteCount++;
            }

            // 🔥 Detect brunch (late first meal)
            if (log.getTimestamp().getHour() >= 11) {
                lateFirstMealCount++;
            }
        }

        // 🔥 Classification Logic

        String userType;

        if (liquidCaloriesCount >= 2 && cigaretteCount >= 2) {
            userType = "HIGH"; // behavior-heavy user (walk + coke type)
        } else if (lateFirstMealCount >= 2) {
            userType = "MID"; // irregular timing (dosa user)
        } else {
            userType = "LOW"; // stable (croissant user)
        }

        user.setUserType(userType);

        // 🔥 Engagement level (basic for now)

        if (logs.size() >= 5) {
            user.setEngagementLevel("HIGH");
        } else if (logs.size() >= 3) {
            user.setEngagementLevel("MEDIUM");
        } else {
            user.setEngagementLevel("LOW");
        }

        // 🔥 Preferred nudge window

        if ("HIGH".equals(userType)) {
            user.setPreferredNudgeWindow("MORNING_WALK");
        } else if ("MID".equals(userType)) {
            user.setPreferredNudgeWindow("EVENING");
        } else {
            user.setPreferredNudgeWindow("LUNCH");
        }

        userRepository.save(user);
    }
}

