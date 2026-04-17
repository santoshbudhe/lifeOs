package com.lifeos.service;

import java.util.UUID;

public interface ConversationStateService {

    boolean canAskQuestion(UUID userId);

    void recordQuestion(UUID userId);

    boolean canSendNudge(UUID userId);

    void recordNudge(UUID userId);

    boolean canSendFollowUp(UUID userId);

    void recordFollowUp(UUID userId);

    void recordMessage(UUID userId);
}
