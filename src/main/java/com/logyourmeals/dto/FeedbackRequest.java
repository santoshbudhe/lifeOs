package com.logyourmeals.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class FeedbackRequest {

    private UUID userId;
    private String messageType;
    private String messageText;
    private String timeWindow;
    private String userType;
    private String feedback; // UP / DOWN
}

