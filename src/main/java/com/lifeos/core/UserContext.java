package com.lifeos.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * TICKET-201: UserContext Implementation
 * this is issue 1 #1 in github
 *
 * Purpose:
 * - Acts as shared state across modules in the processing pipeline
 * - Stores user input, extracted signals, intent, and intermediate outputs
 *
 * Used By:
 * - IntentClassifier
 * - ConversationExtractor
 * - All InsightModules
 * - AgreementEngine
 * - InterventionEngine
 *
 * Notes:
 * - This is a DATA CARRIER (avoid business logic here)
 * - Extensible using generic maps for flexibility
 */
public class UserContext {

    private static final Logger log = LoggerFactory.getLogger(UserContext.class);

    // Unique request tracking (helps debugging)
    private final String requestId;

    // Core user info
    private UUID userId;
    private String rawMessage;

    // Intent (DISCOVERY, LOGGING, QUERY, RESPONSE)
    private String intent;
    private Double intentConfidence;

    // Extracted structured data (from AI)
    private Map<String, Object> extractedData;

    // Module outputs (Energy, Workout, Risk, etc.)
    private Map<String, Object> moduleOutputs;

    // Agreements & decisions
    private Map<String, Object> decisions;

    // Metadata (timestamps, flags, etc.)
    private Map<String, Object> metadata;

    // Final response to user
    private String response;

    public UserContext() {
        this.requestId = UUID.randomUUID().toString();
        this.extractedData = new HashMap<>();
        this.moduleOutputs = new HashMap<>();
        this.decisions = new HashMap<>();
        this.metadata = new HashMap<>();

        log.info("[CONTEXT] Created new UserContext requestId={}", requestId);
    }

    // ----------------------------
    // Basic Getters & Setters
    // ----------------------------

    public String getRequestId() {
        return requestId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
        log.info("[CONTEXT] userId set={}", userId);
    }

    public String getRawMessage() {
        return rawMessage;
    }

    public void setRawMessage(String rawMessage) {
        this.rawMessage = rawMessage;
        log.info("[INPUT] requestId={} message={}", requestId, rawMessage);
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent, Double confidence) {
        this.intent = intent;
        this.intentConfidence = confidence;
        log.info("[INTENT] requestId={} intent={} confidence={}", requestId, intent, confidence);
    }

    public Double getIntentConfidence() {
        return intentConfidence;
    }

    public Map<String, Object> getExtractedData() {
        return extractedData;
    }

    public void putExtractedData(String key, Object value) {
        this.extractedData.put(key, value);
        log.debug("[EXTRACTED] requestId={} key={} value={}", requestId, key, value);
    }

    public Object getExtractedData(String key) {
        return this.extractedData.get(key);
    }

    public Map<String, Object> getModuleOutputs() {
        return moduleOutputs;
    }

    public void putModuleOutput(String moduleName, Object output) {
        this.moduleOutputs.put(moduleName, output);
        log.info("[MODULE] requestId={} module={} output={}", requestId, moduleName, output);
    }

    public Object getModuleOutput(String moduleName) {
        return this.moduleOutputs.get(moduleName);
    }

    public Map<String, Object> getDecisions() {
        return decisions;
    }

    public void putDecision(String key, Object value) {
        this.decisions.put(key, value);
        log.info("[DECISION] requestId={} key={} value={}", requestId, key, value);
    }

    public Object getDecision(String key) {
        return this.decisions.get(key);
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void putMetadata(String key, Object value) {
        this.metadata.put(key, value);
        log.debug("[METADATA] requestId={} key={} value={}", requestId, key, value);
    }

    public Object getMetadata(String key) {
        return this.metadata.get(key);
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
        log.info("[RESPONSE] requestId={} response={}", requestId, response);
    }

    // ----------------------------
    // Utility Methods
    // ----------------------------

    public boolean hasIntent() {
        return this.intent != null;
    }

    public boolean hasExtractedData(String key) {
        return this.extractedData.containsKey(key);
    }

    public boolean hasModuleOutput(String moduleName) {
        return this.moduleOutputs.containsKey(moduleName);
    }

    @Override
    public String toString() {
        return "UserContext{" +
                "requestId='" + requestId + '\'' +
                ", userId='" + userId + '\'' +
                ", intent='" + intent + '\'' +
                ", extractedData=" + extractedData +
                ", moduleOutputs=" + moduleOutputs +
                ", decisions=" + decisions +
                '}';
    }
}
