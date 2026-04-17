package com.logyourmeals.service;

import com.logyourmeals.entity.UserGoal;
import com.logyourmeals.repository.UserGoalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final UserGoalRepository goalRepository;

    @Override
    public UserGoal getOrCreateGoal(UUID userId) {

        return goalRepository.findByUserId(userId)
                .orElseGet(() -> {

                    // 🔥 Default goal (MVP)
                    UserGoal goal = UserGoal.builder()
                            .userId(userId)
                            .goalType("WEIGHT_LOSS")
                            .targetCalories(2000)
                            .createdAt(LocalDateTime.now())
                            .build();

                    return goalRepository.save(goal);
                });
    }

    @Override
    public boolean isSurplusBad(UUID userId) {

        UserGoal goal = getOrCreateGoal(userId);

        return "WEIGHT_LOSS".equals(goal.getGoalType());
    }

    @Override
    public int getTargetCalories(UUID userId) {

        return getOrCreateGoal(userId).getTargetCalories();
    }
}
