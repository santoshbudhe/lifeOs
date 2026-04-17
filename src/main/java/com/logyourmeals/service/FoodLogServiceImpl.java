package com.logyourmeals.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.logyourmeals.dto.BehaviorInsight;
import com.logyourmeals.dto.FoodLogResponse;
import com.logyourmeals.entity.DailySummary;
import com.logyourmeals.entity.FoodLog;
import com.logyourmeals.entity.User;
import com.logyourmeals.repository.FoodLogRepository;
import com.logyourmeals.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FoodLogServiceImpl implements FoodLogService {

    private final FoodLogRepository foodLogRepository;
    private final CalorieEngineService calorieEngineService;
    private final DailySummaryService dailySummaryService;
    private final BehaviorService behaviorService;
    private final ContextExtractorService contextExtractorService;
    private final NudgeService nudgeService;
    private final NetCalorieService netCalorieService;
    private final UserClassificationService userClassificationService;
    private final ResponseBuilderService responseBuilderService;
    private final UserRepository userRepository;
    private final EnergyService energyService;
    private final RiskService riskService;
    private final RoutineService routineService;
    private final RewardService rewardService;
    private final GoalService goalService;



    @Override
    public FoodLogResponse logFood(UUID userId, String foodText) {

        String context = contextExtractorService.extractContext(foodText);

        Integer calories = Optional
                .ofNullable(calorieEngineService.estimateCalories(foodText))
                .orElse(0);

        System.out.println("Calories calculated: " + calories);

        FoodLog log = FoodLog.builder()
                .userId(userId)
                .foodText(foodText)
                .calories(calories)
                .context(context)
                .timestamp(LocalDateTime.now())
                .build();

        foodLogRepository.save(log);

        // 🔥 Update user classification AFTER saving
        userClassificationService.updateUserClassification(userId);

        // 🔥 Behavior analysis
        BehaviorInsight behaviorInsight = behaviorService.analyzeBehavior(userId);

        // 🔥 Update daily summary
        DailySummary summary = dailySummaryService.updateDailyCalories(userId, calories);

        // 🔥 Net calories
        int netCalories = netCalorieService.calculateNetCalories(userId, summary.getTotalCalories());

        // 🔥 Rolling balance
        int last7Days = dailySummaryService.getLastNDaysCalorieBalance(userId, 7);

        // 🔥 Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔥 Get contextual nudge
        String nudge = nudgeService.getNudge(userId);
        
        int energyScore = energyService.calculateEnergyScore(userId, summary.getTotalCalories());
        String energyLevel = energyService.getEnergyLevel(energyScore);
     // optional save
        ((EnergyServiceImpl) energyService).saveSnapshot(userId, energyScore, energyLevel);

        
        int targetCalories = goalService.getTargetCalories(userId);

        
        //Risk
        String riskMessage = riskService.evaluateRisk(
                userId,
                summary.getTotalCalories(),
                targetCalories,
                energyLevel
        );

        //Routine
        String dominantRoutine = routineService.detectRoutineWindow(userId);

        //reward
        
        rewardService.updateReward(userId);
       String rewardMessage = rewardService.getRewardMessage(userId);

       String goalType = goalService.getOrCreateGoal(userId).getGoalType();

       
       
       // 🔥 FINAL RESPONSE VIA RESPONSE BUILDER
        String message = responseBuilderService.buildResponse(
                user,
                log,
                behaviorInsight,
                summary.getTotalCalories(),
                summary.getTargetCalories(),
                last7Days,
                netCalories,
                nudge,
                energyLevel,
                riskMessage,
                dominantRoutine,
                rewardMessage,
                goalType
        );

        return FoodLogResponse.builder()
                .message(message)
                .build();
    }
}
