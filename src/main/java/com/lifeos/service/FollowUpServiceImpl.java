package com.lifeos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.lifeos.entity.ConversationState;
import com.lifeos.entity.User;
import com.lifeos.repository.ConversationStateRepository;
import com.lifeos.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FollowUpServiceImpl implements FollowUpService {

    private final UserRepository userRepository;
    private final ConversationStateRepository stateRepository;
    private final ConversationStateService conversationStateService;

    @Override
    public String getFollowUpMessage(UUID userId) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        ConversationState state = stateRepository.findById(userId).orElse(null);
        if (state == null) return null;

        // 🔥 COOL DOWN CHECK
        if (!conversationStateService.canSendFollowUp(userId)) {
            return null;
        }

        // 🔥 LAST MESSAGE TIME CHECK
        LocalDateTime lastMessage = state.getLastMessageTime();
        if (lastMessage == null) return null;

        long hoursSinceLastMessage = java.time.Duration.between(lastMessage, LocalDateTime.now()).toHours();

        // 🔥 LOGIC BASED ON TIME GAP

        if (hoursSinceLastMessage >= 6 && hoursSinceLastMessage < 12) {
            return handleSameDayGap(userId);
        }

        if (hoursSinceLastMessage >= 12) {
            return handleMissedDay(userId);
        }

        return null;
    }

    // 🔥 SAME DAY FOLLOW-UP (light check-in)
    private String handleSameDayGap(UUID userId) {

        if (!conversationStateService.canAskQuestion(userId)) {
            return null;
        }

        conversationStateService.recordFollowUp(userId);
        conversationStateService.recordQuestion(userId);

        return "Hey — how has your day been going food-wise?";
    }

    // 🔥 MISSED DAY FOLLOW-UP (recovery)
    private String handleMissedDay(UUID userId) {

        if (!conversationStateService.canAskQuestion(userId)) {
            return null;
        }

        conversationStateService.recordFollowUp(userId);
        conversationStateService.recordQuestion(userId);

        return "Looks like we didn’t track much yesterday — want to quickly walk me through what you had?";
    }
}
