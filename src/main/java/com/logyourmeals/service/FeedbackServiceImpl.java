package com.logyourmeals.service;

import com.logyourmeals.entity.InteractionFeedback;
import com.logyourmeals.repository.InteractionFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {

    private final InteractionFeedbackRepository repository;

    @Override
    public void recordFeedback(UUID userId,
                               String messageType,
                               String messageText,
                               String timeWindow,
                               String userType,
                               String feedback) {

        InteractionFeedback data = InteractionFeedback.builder()
                .userId(userId)
                .messageType(messageType)
                .messageText(messageText)
                .timeWindow(timeWindow)
                .userType(userType)
                .feedback(feedback)
                .timestamp(LocalDateTime.now())
                .build();

        repository.save(data);
    }
}
