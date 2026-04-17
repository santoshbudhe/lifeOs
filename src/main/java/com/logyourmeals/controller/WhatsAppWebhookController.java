package com.logyourmeals.controller;

import com.logyourmeals.dto.FoodLogResponse;
import com.logyourmeals.entity.User;
import com.logyourmeals.repository.UserRepository;
import com.logyourmeals.service.FoodLogService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/whatsapp")
@RequiredArgsConstructor
public class WhatsAppWebhookController {

    private final FoodLogService foodLogService;
    private final UserRepository userRepository;

    @PostMapping("/incoming")
    public String receiveMessage(
            @RequestParam("From") String from,
            @RequestParam("Body") String body
    ) {

        System.out.println("📩 Incoming WhatsApp message");
        System.out.println("From: " + from);
        System.out.println("Body: " + body);

        try {
            // 🔥 Generate consistent userId from phone
            UUID userId = UUID.nameUUIDFromBytes(from.getBytes());

            // 🔥 Check if user exists
            User user = userRepository.findById(userId).orElse(null);

            // 🆕 FIRST TIME USER (AUTO ONBOARDING)
            if (user == null) {

                user = User.builder()
                        .id(userId)
                        .phoneNumber(from.replace("whatsapp:", ""))
                        .userType("LOW") // default
                        .build();

                userRepository.save(user);

                System.out.println("🆕 New user created: " + user.getPhoneNumber());

                return "Hey 👋 I’ll help you track your food and understand your eating patterns.\n\nJust tell me what you ate today 😊";
            }

            // 🔁 EXISTING USER FLOW
            FoodLogResponse response = foodLogService.logFood(userId, body);

            return response.getMessage();

        } catch (Exception e) {

            e.printStackTrace();

            return "Something went wrong — try again 😊";
        }
    }
}
