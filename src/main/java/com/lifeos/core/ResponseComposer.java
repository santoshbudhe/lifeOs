package com.lifeos.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * TICKET-208: ResponseComposer
 *
 * Purpose:
 * - Builds final user response from UserContext
 *
 * Used By:
 * - PipelineExecutor (final step)
 * - WhatsAppService (integration later)
 *
 * Notes:
 * - Temporary rule-based response generation
 * - Will later integrate with AI for phrasing
 */
@Component
public class ResponseComposer {

    private static final Logger log = LoggerFactory.getLogger(ResponseComposer.class);

    public void compose(UserContext context) {

        String intent = context.getIntent();
        Map<String, Object> outputs = context.getModuleOutputs();

        log.info("[RESPONSE-COMPOSER] requestId={} intent={}", context.getRequestId(), intent);

        String response;

        switch (intent) {

            case "LOGGING":
                response = handleLoggingResponse(outputs);
                break;

            case "DISCOVERY":
                response = handleDiscoveryResponse();
                break;

            case "QUERY":
                response = handleQueryResponse();
                break;

            case "RESPONSE":
                response = handleUserReply();
                break;

            default:
                response = "Got it.";
        }

        context.setResponse(response);

        log.info("[RESPONSE-COMPOSER] requestId={} finalResponse={}",
                context.getRequestId(), response);
    }

    // ---------------- LOGGING ----------------

    private String handleLoggingResponse(Map<String, Object> outputs) {

        Object insight = outputs.get("tracking_insight");

        if (insight != null) {
            return insight.toString();
        }

        return "Got it — noted.";
    }

    // ---------------- DISCOVERY ----------------

    private String handleDiscoveryResponse() {
        return "Tell me a bit more about what's going on.";
    }

    // ---------------- QUERY ----------------

    private String handleQueryResponse() {
        return "Let me think about that.";
    }

    // ---------------- RESPONSE ----------------

    private String handleUserReply() {
        return "Got it.";
    }
}
