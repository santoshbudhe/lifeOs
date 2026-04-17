package com.logyourmeals.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InterventionServiceImpl implements InterventionService {

    private final RoutineService routineService;
    private final EnergyService energyService;
    private final RiskService riskService;
    private final GoalService goalService;

    @Override
    public String generateIntervention(UUID userId) {

        String routine = routineService.detectRoutineWindow(userId);
        String energy = null; // energyService.calculateEnergyState(userId);
        String goal = goalService.getOrCreateGoal(userId).getGoalType();

        // 🔥 PRIORITY 1: LOW ENERGY
        if ("LOW".equals(energy)) {
            return "You're a bit low on energy right now — this is usually when quick choices creep in. Going a bit lighter here could help.";
        }

        // 🔥 PRIORITY 2: ROUTINE WINDOW
        if ("EVENING".equals(routine) || "NIGHT".equals(routine)) {
            return "This is your usual window where things add up. Keeping it lighter here can make a big difference.";
        }

        // 🔥 PRIORITY 3: GOAL BASED
        if ("WEIGHT_LOSS".equals(goal)) {
            return "Staying within your intake here will help you stay on track for your goal.";
        }

        return null;
    }
}

