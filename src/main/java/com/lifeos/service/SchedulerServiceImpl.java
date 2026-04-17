package com.lifeos.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lifeos.entity.User;
import com.lifeos.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final UserRepository userRepository;
    private final FollowUpService followUpService;
    private final NudgeService nudgeService;

    // 🔥 Runs every 1 hour
    @Override
    @Scheduled(fixedRate = 60 * 60 * 1000)
    public void runScheduler() {

        System.out.println("🔁 Scheduler running...");

        List<User> users = userRepository.findAll();

        for (User user : users) {

            UUID userId = user.getId();

            // 🔥 1. FOLLOW-UP CHECK
            String followUp = followUpService.getFollowUpMessage(userId);

            if (followUp != null) {
                sendMessage(userId, followUp);
                continue; // prioritize follow-up over nudge
            }

            // 🔥 2. NUDGE CHECK
            String nudge = nudgeService.getNudge(userId);

            if (nudge != null) {
                sendMessage(userId, nudge);
            }
        }
    }

    // 🔥 TEMP: Console output (later → WhatsApp)
    private void sendMessage(UUID userId, String message) {

        System.out.println("📩 Sending to " + userId + ":");
        System.out.println(message);
        System.out.println("----------------------------");
    }
}
