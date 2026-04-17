package com.lifeos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.lifeos.dto.FeedbackRequest;
import com.lifeos.service.FeedbackService;

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

