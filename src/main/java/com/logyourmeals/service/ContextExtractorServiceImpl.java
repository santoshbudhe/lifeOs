package com.logyourmeals.service;


import org.springframework.stereotype.Service;


@Service
public class ContextExtractorServiceImpl implements ContextExtractorService {


    @Override
    public String extractContext(String text) {


        String lower = text.toLowerCase();


        if (lower.contains("bored")) return "bored";
        if (lower.contains("stress") || lower.contains("stressed")) return "stress";
        if (lower.contains("tired")) return "tired";
        if (lower.contains("party") || lower.contains("friends")) return "social";
        if (lower.contains("gym") || lower.contains("workout")) return "post-workout";
        if (lower.contains("tv") || lower.contains("watching")) return "screen-time";


        return null;
    }
}
