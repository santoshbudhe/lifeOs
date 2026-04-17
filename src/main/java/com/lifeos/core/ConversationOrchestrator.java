package com.lifeos.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lifeos.IntentClassifier;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

/**
 * TICKET-209: ConversationOrchestrator
 *
 * Purpose:
 * - Central brain that orchestrates full conversation flow
 *
 * Flow:
 * - Create context
 * - Classify intent
 * - Route modules
 * - Execute pipeline
 * - Compose response
 *
 * Used By:
 * - WhatsAppService (next step)
 */
@Component
public class ConversationOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(ConversationOrchestrator.class);

    @Autowired
    private IntentClassifier intentClassifier;

    @Autowired
    private Router router;

    @Autowired
    private ResponseComposer responseComposer;

    /**
     * Main entry point
     */
    public String processMessage(UUID userId, String message) {

        log.info("[ORCHESTRATOR-START] userId={} message={}", userId, message);

        // 1️⃣ Create context
        UserContext context = new UserContext();
        context.setUserId(userId);
        context.setRawMessage(message);

        // 2️⃣ Classify intent
        intentClassifier.classify(context);

        // 3️⃣ Route modules
        List<InsightModule> modules = router.route(context);

        // 4️⃣ Execute pipeline
        PipelineExecutor pipeline = new PipelineExecutor();
        pipeline.addModules(modules);
        pipeline.execute(context);

        // 5️⃣ Compose response
        responseComposer.compose(context);

        String response = context.getResponse();

        log.info("[ORCHESTRATOR-END] requestId={} response={}",
                context.getRequestId(), response);

        return response;
    }
}
