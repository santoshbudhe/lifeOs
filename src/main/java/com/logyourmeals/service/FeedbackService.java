package com.logyourmeals.service;

import java.util.UUID;

public interface FeedbackService {

    void recordFeedback(UUID userId,
                        String messageType,
                        String messageText,
                        String timeWindow,
                        String userType,
                        String feedback);
}
