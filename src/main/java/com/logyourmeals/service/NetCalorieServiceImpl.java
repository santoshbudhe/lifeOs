package com.logyourmeals.service;

import com.logyourmeals.entity.User;
import com.logyourmeals.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NetCalorieServiceImpl implements NetCalorieService {

    private final UserRepository userRepository;
    private final BmrService bmrService;
    private final ActivityService activityService;

    @Override
    public int calculateNetCalories(UUID userId, int intakeCalories) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return intakeCalories - 2000; // fallback
        }

        int bmr = bmrService.calculateBmr(user);
        
        int activityBurn = activityService.getTodayActivityCalories(userId);


        return intakeCalories - bmr - activityBurn;
    }
}
