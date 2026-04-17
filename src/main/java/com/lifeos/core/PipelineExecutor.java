package com.lifeos.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * TICKET-203: PipelineExecutor
 *
 * Purpose:
 * - Orchestrates execution of all InsightModules
 * - Passes UserContext through a sequence of modules
 *
 * Used By:
 * - WhatsAppService (future integration)
 * - Controllers (eventually)
 *
 * Notes:
 * - Modules are executed in order
 * - Each module enriches UserContext
 * - No business logic here — only orchestration
 */
public class PipelineExecutor {

    private static final Logger log = LoggerFactory.getLogger(PipelineExecutor.class);

    private final List<InsightModule> modules;

    public PipelineExecutor() {
        this.modules = new ArrayList<>();
    }

    /**
     * Add module to pipeline
     */
    public void addModule(InsightModule module) {
        modules.add(module);
        log.info("[PIPELINE] Module added={}", module.getModuleName());
    }

    /**
     * Execute full pipeline
     */
    public void execute(UserContext context) {

        log.info("[PIPELINE-START] requestId={} totalModules={}",
                context.getRequestId(), modules.size());

        long start = System.currentTimeMillis();

        for (InsightModule module : modules) {
            module.execute(context);
        }

        long duration = System.currentTimeMillis() - start;

        log.info("[PIPELINE-END] requestId={} durationMs={}",
                context.getRequestId(), duration);
    }

    /**
     * Convenience method to register multiple modules
     */
    public void addModules(List<InsightModule> modules) {
        for (InsightModule module : modules) {
            addModule(module);
        }
    }
}


