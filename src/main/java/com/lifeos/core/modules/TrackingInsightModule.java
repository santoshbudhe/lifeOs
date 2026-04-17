package com.lifeos.core.modules;

import com.lifeos.core.InsightModule;
import com.lifeos.core.UserContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * TICKET-207: TrackingInsightModule
 *
 * Purpose:
 * - Generates insights based on logged activity/food
 * - Adds intelligence layer after logging
 *
 * Notes:
 * - Uses UserContext.moduleOutputs
 * - Will later integrate with Energy, Sleep, Nutrition modules
 */
@Component
public class TrackingInsightModule implements InsightModule {

    private static final Logger log = LoggerFactory.getLogger(TrackingInsightModule.class);

    @Override
    public void process(UserContext context) {

        Object loggingResult = context.getModuleOutput("logging");

        if (loggingResult == null) {
            log.info("[TRACKING] requestId={} no logging data found", context.getRequestId());
            return;
        }

        log.info("[TRACKING] requestId={} analyzing loggingResult={}",
                context.getRequestId(), loggingResult);

        String insight = generateInsight(loggingResult.toString());

        context.putModuleOutput("tracking_insight", insight);

        log.info("[TRACKING] requestId={} insight={}",
                context.getRequestId(), insight);
    }

    private String generateInsight(String loggingResult) {

        // VERY BASIC for now — will evolve

        if ("workout_logged".equals(loggingResult)) {
            return "Nice — you worked out today. Keep consistency going.";
        }

        if ("food_logged".equals(loggingResult)) {
            return "Good — food logged. Tracking consistently helps long term.";
        }

        return "Activity noted.";
    }

    @Override
    public String getModuleName() {
        return "TrackingInsightModule";
    }

    @Override
    public boolean isApplicable(UserContext context) {
        return "LOGGING".equalsIgnoreCase(context.getIntent());
    }
}
