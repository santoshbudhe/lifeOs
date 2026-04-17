package com.logyourmeals.service;

import com.logyourmeals.dto.BehaviorInsight;
import com.logyourmeals.entity.FoodLog;
import com.logyourmeals.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResponseBuilderServiceImpl implements ResponseBuilderService {

    private final ConversationStateService conversationStateService;

    @Override
    public String buildResponse(User user,
                                FoodLog log,
                                BehaviorInsight insight,
                                int totalToday,
                                int target,
                                int last7Days,
                                int netCalories,
                                String nudge,
                                String energyLevel,
                                String riskMessage,
                                String dominantRoutine,
                                String rewardMessage,
                                String goalType) {

        StringBuilder sb = new StringBuilder();

        // 🔥 1. BASIC RESPONSE
        sb.append("~").append(log.getCalories()).append(" kcal added\n");
        sb.append("Today: ").append(totalToday).append(" / ").append(target).append("\n");

        // 🔥 2. 7 DAY BALANCE
        if (last7Days > 0) {
            sb.append("Last 7 days: +").append(last7Days).append(" kcal (surplus)\n");
        } else if (last7Days < 0) {
            sb.append("Last 7 days: ").append(last7Days).append(" kcal (deficit)\n");
        } else {
            sb.append("Last 7 days: balanced\n");
        }

        // 🔥 3. NET CALORIES (IMPORTANT ADD)
        sb.append("\nNet: ").append(netCalories).append(" kcal ");

        if (netCalories > 0) {
            sb.append("(Surplus)\n");
        } else if (netCalories < 0) {
            sb.append("(Deficit)\n");
        } else {
            sb.append("(Balanced)\n");
        }

        String userType = user.getUserType();

        // 🔥 4. USER TYPE LOGIC

        if ("HIGH".equals(userType)) {

            // 👉 Awareness only (NO questions)
            if (insight != null) {
                sb.append("\n⚠️ ").append(insight.getMessage());
            }

        } else if ("MID".equals(userType)) {

            // 👉 Insight
            if (insight != null) {
                sb.append("\n⚠️ ").append(insight.getMessage());
            }

            // 👉 Ask ONLY if allowed
            if (conversationStateService.canAskQuestion(user.getId())) {
                sb.append("\n\nHow did this fit into your day overall?");
                conversationStateService.recordQuestion(user.getId());
            }

        } else {

            // 👉 LOW user → keep it light
            sb.append("\n\nLooks consistent so far 👍");
        }

        // 🔥 5. NUDGE (CONTROLLED)

        if (nudge != null && conversationStateService.canSendNudge(user.getId())) {
            sb.append("\n\n💡 ").append(nudge);
            conversationStateService.recordNudge(user.getId());
        }
        
        // Energy level
        
        sb.append("\nEnergy: ").append(energyLevel);
        
        // Risk
        
        if (riskMessage != null) {
            sb.append("\n\n⚠️ ").append(riskMessage);
        }
        
        //Routine
        if (dominantRoutine != null 
        	    && shouldShowRoutineInsight(user)
        	    && Math.random() < 0.4) {

        	    sb.append("\n\n📊 Pattern: You tend to eat more during ")
        	      .append(dominantRoutine.toLowerCase())
        	      .append(" hours.");
        	}
        
        //goal
        if ("WEIGHT_LOSS".equals(goalType)) {

            if (netCalories > 0) {
                sb.append("\n\nYou're slightly above your goal today.");
            } else {
                sb.append("\n\nYou're within your goal — good progress.");
            }

        } else if ("MUSCLE_GAIN".equals(goalType)) {

            if (netCalories > 0) {
                sb.append("\n\nYou're fueling your body well.");
            } else {
                sb.append("\n\nYou might need a bit more intake today.");
            }

        } else {

            sb.append("\n\nTracking looks consistent so far.");
        }
         


        return sb.toString();
    }
    
    
    private boolean shouldShowRoutineInsight(User user) {

        // Show only for MID/HIGH users
        return "MID".equals(user.getUserType()) || "HIGH".equals(user.getUserType());
    }

}



