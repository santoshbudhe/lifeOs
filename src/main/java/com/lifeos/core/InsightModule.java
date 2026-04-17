package com.lifeos.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TICKET-202: InsightModule Interface
 *
 * Purpose:
 * - Defines a standard processing contract for all modules
 * - Each module receives UserContext, processes it, and enriches it
 *
 * Used By:
 * - PipelineExecutor
 * - All modules (Energy, Workout, Risk, etc.)
 *
 * Notes:
 * - Modules should NOT return data directly
 * - They must update UserContext
 * - Keep modules independent and composable
 */
public interface InsightModule {

    Logger log = LoggerFactory.getLogger(InsightModule.class);

    /**
     * Main processing method
     *
     * @param context shared UserContext object
     */
    void process(UserContext context);

    /**
     * Module name (used for logging + identification)
     */
    String getModuleName();

    /**
     * Whether this module should execute for the current context
     *
     * Example:
     * - EnergyModule → always true
     * - WorkoutModule → only if workout data present
     */
    default boolean isApplicable(UserContext context) {
        return true;
    }

    /**
     * Wrapper method to safely execute module with logging
     */
    default void execute(UserContext context) {
        String moduleName = getModuleName();

        try {
            if (!isApplicable(context)) {
                log.info("[MODULE-SKIP] requestId={} module={}", context.getRequestId(), moduleName);
                return;
            }

            log.info("[MODULE-START] requestId={} module={}", context.getRequestId(), moduleName);

            long start = System.currentTimeMillis();

            process(context);

            long duration = System.currentTimeMillis() - start;

            log.info("[MODULE-END] requestId={} module={} durationMs={}",
                    context.getRequestId(), moduleName, duration);

        } catch (Exception e) {
            log.error("[MODULE-ERROR] requestId={} module={} error={}",
                    context.getRequestId(), moduleName, e.getMessage(), e);
        }
    }
}

