package com.logyourmeals.service;

import com.logyourmeals.entity.ConversationState;
import com.logyourmeals.repository.ConversationStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConversationStateServiceImpl implements ConversationStateService {

    private final ConversationStateRepository repository;

    private ConversationState getOrCreate(UUID userId) {

        return repository.findById(userId).orElseGet(() -> {
            ConversationState state = ConversationState.builder()
                    .userId(userId)
                    .questionsAskedToday(0)
                    .lastResetDate(LocalDateTime.now())
                    .build();
            return repository.save(state);
        });
    }

    private void resetIfNewDay(ConversationState state) {

        if (state.getLastResetDate() == null) {
            state.setLastResetDate(LocalDateTime.now());
            return;
        }

        if (state.getLastResetDate().toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            state.setQuestionsAskedToday(0);
            state.setLastResetDate(LocalDateTime.now());
        }
    }

    // 🔥 QUESTION CONTROL

    @Override
    public boolean canAskQuestion(UUID userId) {

        ConversationState state = getOrCreate(userId);
        resetIfNewDay(state);

        return state.getQuestionsAskedToday() < 2;
    }

    @Override
    public void recordQuestion(UUID userId) {

        ConversationState state = getOrCreate(userId);

        state.setQuestionsAskedToday(state.getQuestionsAskedToday() + 1);
        state.setLastQuestionTime(LocalDateTime.now());

        repository.save(state);
    }

    // 🔥 NUDGE CONTROL (min gap: 3 hours)

    @Override
    public boolean canSendNudge(UUID userId) {

        ConversationState state = getOrCreate(userId);

        if (state.getLastNudgeTime() == null) return true;

        return state.getLastNudgeTime().isBefore(LocalDateTime.now().minusHours(3));
    }

    @Override
    public void recordNudge(UUID userId) {

        ConversationState state = getOrCreate(userId);

        state.setLastNudgeTime(LocalDateTime.now());

        repository.save(state);
    }

    // 🔥 FOLLOW-UP CONTROL (min gap: 6 hours)

    @Override
    public boolean canSendFollowUp(UUID userId) {

        ConversationState state = getOrCreate(userId);

        if (state.getLastFollowUpTime() == null) return true;

        return state.getLastFollowUpTime().isBefore(LocalDateTime.now().minusHours(6));
    }

    @Override
    public void recordFollowUp(UUID userId) {

        ConversationState state = getOrCreate(userId);

        state.setLastFollowUpTime(LocalDateTime.now());

        repository.save(state);
    }

    // 🔥 GENERAL MESSAGE TRACKING

    @Override
    public void recordMessage(UUID userId) {

        ConversationState state = getOrCreate(userId);

        state.setLastMessageTime(LocalDateTime.now());

        repository.save(state);
    }
}

