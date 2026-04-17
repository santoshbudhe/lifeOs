package com.logyourmeals.scheduler;


import com.logyourmeals.entity.User;
import com.logyourmeals.repository.UserRepository;
import com.logyourmeals.service.NudgeService;
import com.logyourmeals.service.WhatsAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


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
