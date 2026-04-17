package com.logyourmeals.service;

import com.logyourmeals.entity.UserReward;
import com.logyourmeals.repository.UserRewardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RewardServiceImpl implements RewardService {

    private final UserRewardRepository rewardRepository;

    @Override
    public void updateReward(UUID userId) {

        UserReward reward = rewardRepository.findByUserId(userId)
                .orElse(UserReward.builder()
                        .userId(userId)
                        .streakDays(0)
                        .score(50)
                        .lastActiveDate(LocalDate.now())
                        .build());

        LocalDate today = LocalDate.now();

        if (reward.getLastActiveDate() != null &&
                reward.getLastActiveDate().plusDays(1).equals(today)) {

            reward.setStreakDays(reward.getStreakDays() + 1);

        } else if (!today.equals(reward.getLastActiveDate())) {

            reward.setStreakDays(1); // reset
        }

        // 🔥 Simple scoring
        reward.setScore(reward.getScore() + 2);

        reward.setLastActiveDate(today);

        rewardRepository.save(reward);
    }

    @Override
    public String getRewardMessage(UUID userId) {

        UserReward reward = rewardRepository.findByUserId(userId).orElse(null);

        if (reward == null) return null;

        int streak = reward.getStreakDays();
        int score = reward.getScore();

        // 🔥 milestone rewards
        if (streak == 3) {
            return "Nice — you're building consistency now 🔥";
        }

        if (streak == 7) {
            return "7-day streak — this is where real change starts 💪";
        }

        if (score > 80) {
            return "You're in control right now — keep this momentum going.";
        }

        return null;
    }
}
