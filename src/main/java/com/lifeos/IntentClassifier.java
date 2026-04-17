package com.lifeos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.lifeos.core.UserContext;

import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * TICKET-204: IntentClassifier
 *
 * Purpose:
 * - Classifies user input into intent categories
 * - Uses AI (LLM) + fallback heuristics
 *
 * Output:
 * - Sets intent + confidence in UserContext
 *
 * Intents:
 * - DISCOVERY (problems, emotions)
 * - LOGGING (gym, food, tracking)
 * - QUERY (questions)
 * - RESPONSE (reply to system)
 */
@Component
public class IntentClassifier {

    private static final Logger log = LoggerFactory.getLogger(IntentClassifier.class);

    @Autowired
    private RestTemplate restTemplate;

    // TODO: Move to config later
    private static final String AI_ENDPOINT = "YOUR_AI_ENDPOINT_HERE";

    public void classify(UserContext context) {

        String message = context.getRawMessage();

        log.info("[INTENT-CLASSIFY] requestId={} message={}", context.getRequestId(), message);

        try {
            // Step 1: AI Classification
            Map<String, Object> aiResult = callAI(message);

            String intent = (String) aiResult.get("intent");
            Double confidence = Double.valueOf(aiResult.get("confidence").toString());

            context.setIntent(intent, confidence);

        } catch (Exception e) {

            log.error("[INTENT-AI-FAIL] requestId={} falling back to heuristic",
                    context.getRequestId(), e);

            // Step 2: Fallback heuristic
            fallbackClassification(context);
        }
    }

    private Map<String, Object> callAI(String message) {

        Map<String, Object> request = new HashMap<>();
        request.put("task", "intent_classification");
        request.put("message", message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                AI_ENDPOINT,
                HttpMethod.POST,
                entity,
                Map.class
        );

        log.info("[INTENT-AI-RESPONSE] {}", response.getBody());

        return response.getBody();
    }

    /**
     * Simple fallback if AI fails
     */
    private void fallbackClassification(UserContext context) {

        String message = context.getRawMessage().toLowerCase();

        String intent = "DISCOVERY"; // default
        double confidence = 0.5;

        if (message.contains("did") || message.contains("ate") || message.contains("worked out")) {
            intent = "LOGGING";
            confidence = 0.6;
        } else if (message.contains("?")) {
            intent = "QUERY";
            confidence = 0.6;
        } else if (message.contains("yes") || message.contains("no")) {
            intent = "RESPONSE";
            confidence = 0.5;
        }

        context.setIntent(intent, confidence);

        log.info("[INTENT-FALLBACK] requestId={} intent={}", context.getRequestId(), intent);
    }
}


