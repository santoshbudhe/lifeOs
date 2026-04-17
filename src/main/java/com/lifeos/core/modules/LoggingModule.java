package com.lifeos.core.modules;

import com.lifeos.core.InsightModule;
import com.lifeos.core.UserContext;
import com.lifeos.service.ActivityService;
import com.lifeos.service.FoodLogService;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * TICKET-206: LoggingModule
 * Ref #6
 * Purpose:
 * - Handles LOGGING intent (workout, food, activity)
 * - Extracts basic signals from user message
 * - Delegates to existing services
 *
 * Notes:
 * - This is a bridge between OLD services and NEW pipeline
 * - Will evolve later into AI-based structured logging
 */
@Component
public class LoggingModule implements InsightModule {

    private static final Logger log = LoggerFactory.getLogger(LoggingModule.class);

    @Autowired
    private ActivityService activityService;

    @Autowired
    private FoodLogService foodLogService;

    @Override
    public void process(UserContext context) {

        String message = context.getRawMessage();
        UUID userId = context.getUserId();

        log.info("[LOGGING] requestId={} processing message={}", context.getRequestId(), message);

        // VERY SIMPLE heuristic for now (we will upgrade later)
        if (isWorkout(message)) {
            handleWorkout(context, userId, message);
        } else if (isFood(message)) {
            handleFood(context, userId, message);
        } else {
            log.info("[LOGGING] requestId={} unknown logging type", context.getRequestId());
            context.putModuleOutput("logging", "unknown");
        }
    }

    private void handleWorkout(UserContext context, UUID userId, String message) {
        try {
            log.info("[LOGGING] Workout detected");

            activityService.logActivity(userId, message);

            context.putModuleOutput("logging", "workout_logged");

        } catch (Exception e) {
            log.error("[LOGGING-ERROR] Workout logging failed", e);
        }
    }

    private void handleFood(UserContext context, UUID userId, String message) {
        try {
            log.info("[LOGGING] Food detected");

            foodLogService.logFood(userId, message);

            context.putModuleOutput("logging", "food_logged");

        } catch (Exception e) {
            log.error("[LOGGING-ERROR] Food logging failed", e);
        }
    }

    private boolean isWorkout(String message) {
        String msg = message.toLowerCase();
        return msg.contains("workout") ||
               msg.contains("bench") ||
               msg.contains("gym") ||
               msg.contains("run");
    }

    private boolean isFood(String message) {
        String msg = message.toLowerCase();
        return msg.contains("ate") ||
               msg.contains("food") ||
               msg.contains("meal") ||
               msg.contains("rice");
    }

    @Override
    public String getModuleName() {
        return "LoggingModule";
    }

    @Override
    public boolean isApplicable(UserContext context) {
        return "LOGGING".equalsIgnoreCase(context.getIntent());
    }
}
