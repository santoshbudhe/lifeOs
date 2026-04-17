package com.logyourmeals.service;

import com.logyourmeals.entity.User;
import com.logyourmeals.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NudgeServiceImpl implements NudgeService {

    private final UserRepository userRepository;
    private final ConversationStateService conversationStateService;

    @Override
    public String getNudge(UUID userId) {

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return null;

        // 🔥 CHECK COOLDOWN FIRST
        if (!conversationStateService.canSendNudge(userId)) {
            return null;
        }

        String userType = user.getUserType();
        if (userType == null) return null;

        int hour = LocalDateTime.now().getHour();
        String timeWindow = getTimeWindow(hour);

        String nudge = null;

        switch (userType) {
            case "HIGH":
                nudge = getHighIntensityNudge(timeWindow);
                break;
            case "MID":
                nudge = getMidIntensityNudge(timeWindow);
                break;
            case "LOW":
                nudge = getLowIntensityNudge(timeWindow);
                break;
        }

        // 🔥 RECORD ONLY IF NUDGE EXISTS
        if (nudge != null) {
            conversationStateService.recordNudge(userId);
        }

        return nudge;
    }

    private String getTimeWindow(int hour) {
        if (hour >= 6 && hour <= 10) return "MORNING";
        if (hour >= 12 && hour <= 14) return "LUNCH";
        if (hour >= 17 && hour <= 20) return "EVENING";
        return "OTHER";
    }

    // 🔥 HIGH → intervention user
    private String getHighIntensityNudge(String timeWindow) {
        switch (timeWindow) {
            case "MORNING":
                return "You're likely on your walk now — this is usually where those drinks creep in. Keeping it lighter today could make a big difference 👍";
            case "EVENING":
                return "Evenings are where things tend to add up — keeping it a bit lighter today can help you stay on track.";
            default:
                return null;
        }
    }

    // 🔥 MID → pattern user
    private String getMidIntensityNudge(String timeWindow) {
        switch (timeWindow) {
            case "EVENING":
                return "Your meal timing earlier can affect how the rest of the day goes — worth keeping an eye on how hungry you feel later.";
            default:
                return null;
        }
    }

    // 🔥 LOW → stable user
    private String getLowIntensityNudge(String timeWindow) {
        switch (timeWindow) {
            case "LUNCH":
                return "Mornings look quite steady — I’ll keep tracking how the rest of your day shapes up.";
            case "EVENING":
                return "Just keeping an eye on your daily pattern — consistency looks good so far 👍";
            default:
                return null;
        }
    }
}
