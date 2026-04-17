package com.lifeos.controller;

import com.lifeos.core.ConversationOrchestrator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;
import java.util.Map;

/**
 * TICKET-210: TestController
 *
 * Purpose:
 * - Provides REST endpoint to test conversation flow via Postman
 *
 * Endpoint:
 * - POST /test/message
 *
 * Input:
 * {
 *   "userId": "uuid-string",
 *   "message": "text message"
 * }
 *
 * Output:
 * {
 *   "response": "system response"
 * }
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private ConversationOrchestrator orchestrator;

    @PostMapping("/message")
    public Map<String, String> processMessage(@RequestBody Map<String, String> request) {

        String userIdStr = request.get("userId");
        String message = request.get("message");

        log.info("[TEST-CONTROLLER] Received request userId={} message={}", userIdStr, message);

        UUID userId;

        try {
            userId = UUID.fromString(userIdStr);
        } catch (Exception e) {
            log.error("[TEST-CONTROLLER] Invalid UUID={}", userIdStr);
            return Map.of("response", "Invalid userId format");
        }

        String response = orchestrator.processMessage(userId, message);

        log.info("[TEST-CONTROLLER] Response={}", response);

        return Map.of("response", response);
    }
}
