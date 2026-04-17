package com.lifeos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lifeos.entity.EnergySnapshot;
import com.lifeos.repository.EnergyRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnergyServiceImpl implements EnergyService {

    private final EnergyRepository energyRepository;
    private final ActivityService activityService; // already created earlier

    @Override
    public int calculateEnergyScore(UUID userId, int caloriesIn) {

        int score = 50; // base

        // 🔼 FOOD CONTRIBUTION
        if (caloriesIn > 500) {
            score += 10;
        }

        // 🔽 ACTIVITY (burn)
        int caloriesBurned = activityService.getTodayActivityCalories(userId);

        if (caloriesBurned > 400) {
            score -= 30;
        } else if (caloriesBurned > 200) {
            score -= 20;
        } else if (caloriesBurned > 100) {
            score -= 10;
        }


        // Clamp
        if (score > 100) score = 100;
        if (score < 0) score = 0;

        return score;
    }

    @Override
    public String getEnergyLevel(int score) {
        if (score >= 70) return "HIGH";
        if (score >= 40) return "MEDIUM";
        return "LOW";
    }

    public void saveSnapshot(UUID userId, int score, String level) {
        EnergySnapshot snapshot = EnergySnapshot.builder()
                .userId(userId)
                .energyScore(score)
                .energyLevel(level)
                .timestamp(LocalDateTime.now())
                .build();

        energyRepository.save(snapshot);
    }
}


