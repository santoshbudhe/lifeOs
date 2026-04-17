package com.logyourmeals.controller;

import com.logyourmeals.service.FollowUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/followup")
@RequiredArgsConstructor
public class FollowUpController {

    private final FollowUpService followUpService;

    @GetMapping("/{userId}")
    public String getFollowUp(@PathVariable UUID userId) {
        return followUpService.getFollowUpMessage(userId);
    }
}
