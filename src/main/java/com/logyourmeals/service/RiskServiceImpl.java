package com.logyourmeals.service;

import com.logyourmeals.entity.RiskProfile;
import com.logyourmeals.repository.RiskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RiskServiceImpl implements RiskService {

    private final RiskRepository riskRepository;

    @Override
    public String evaluateRisk(UUID userId,
                               int totalCalories,
                               int targetCalories,
                               String energyLevel) {

        String message = null;
        String riskType = null;
        int score = 0;

        // 🔥 1. OVER EATING RISK
        if (totalCalories > targetCalories + 300) {
            riskType = "OVER_EATING";
            score = 80;
            message = "You’re going above your usual range today — this could slow down your progress.";
        }

        // 🔥 2. LOW ENERGY RISK
        else if ("LOW".equals(energyLevel)) {
            riskType = "LOW_ENERGY";
            score = 70;
            message = "Your energy seems low — this is when cravings or impulsive choices usually kick in.";
        }

        // 🔥 3. LATE EATING RISK
        else {
            int hour = LocalDateTime.now().getHour();
            if (hour >= 21 && totalCalories > (targetCalories * 0.6)) {
                riskType = "LATE_EATING";
                score = 60;
                message = "You’re entering your usual late window — this is where extra calories tend to slip in.";
            }
        }

        // Save only if risk detected
        if (riskType != null) {
            RiskProfile risk = RiskProfile.builder()
                    .userId(userId)
                    .riskType(riskType)
                    .riskScore(score)
                    .message(message)
                    .timestamp(LocalDateTime.now())
                    .build();

            riskRepository.save(risk);
        }

        return message;
    }
}


