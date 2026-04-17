package com.logyourmeals.controller;

import com.logyourmeals.dto.FeedbackRequest;
import com.logyourmeals.service.FeedbackService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @PostMapping
    public String submitFeedback(@RequestBody FeedbackRequest request) {

        feedbackService.recordFeedback(
                request.getUserId(),
                request.getMessageType(),
                request.getMessageText(),
                request.getTimeWindow(),
                request.getUserType(),
                request.getFeedback()
        );

        return "Feedback recorded successfully";
    }
}

