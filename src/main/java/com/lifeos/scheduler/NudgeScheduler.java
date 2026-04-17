package com.lifeos.scheduler;


import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.lifeos.entity.User;
import com.lifeos.repository.UserRepository;
import com.lifeos.service.NudgeService;
import com.lifeos.service.WhatsAppService;

import java.util.List;


@Component
@RequiredArgsConstructor
public class NudgeScheduler {


    private final UserRepository userRepository;
    private final NudgeService nudgeService;
    private final WhatsAppService whatsAppService;


    // Runs every hour
    @Scheduled(fixedRate = 60000)
    public void sendNudges() {


        List<User> users = userRepository.findAll();


        for (User user : users) {


            String nudge = nudgeService.getNudge(user.getId());


            if (nudge != null) {
                whatsAppService.sendMessage(user.getPhoneNumber(), nudge);
            }
        }
    }
}
