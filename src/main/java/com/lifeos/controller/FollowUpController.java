package com.lifeos.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.lifeos.service.FollowUpService;

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
