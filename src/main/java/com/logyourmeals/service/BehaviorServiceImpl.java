package com.logyourmeals.service;


import com.logyourmeals.dto.BehaviorInsight;
import com.logyourmeals.repository.FoodLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class BehaviorServiceImpl implements BehaviorService {


    private final FoodLogRepository foodLogRepository;


    @Override
    public BehaviorInsight analyzeBehavior(UUID userId) {


        TimeResult time = analyzeTimeBehavior(userId);
        ContextResult context = analyzeContextBehavior(userId);


        if (time != null && context != null) {
            return BehaviorInsight.builder()
                    .message("You tend to eat " + time.label + " when " + context.label)
                    .frequency(Math.min(time.count, context.count))
                    .build();
        }


        if (context != null) {
            return BehaviorInsight.builder()
                    .message("You often eat when " + context.label)
                    .frequency(context.count)
                    .build();
        }


        if (time != null) {
            return BehaviorInsight.builder()
                    .message("You tend to eat " + time.label)
                    .frequency(time.count)
                    .build();
        }


        return null;
    }


    // ---------------- TIME ----------------


    private TimeResult analyzeTimeBehavior(UUID userId) {


        int daysWithLateEating = 0;


        for (int i = 0; i < 7; i++) {


            LocalDateTime cutoff = LocalDateTime.now().minusDays(i)
                    .withHour(20).withMinute(0).withSecond(0);


            long count = foodLogRepository
                    .countByUserIdAndTimestampAfter(userId, cutoff);


            if (count > 0) daysWithLateEating++;
        }


        if (daysWithLateEating >= 3) {
            return new TimeResult("late at night", daysWithLateEating);
        }


        return null;
    }


    // ---------------- CONTEXT ----------------


    private ContextResult analyzeContextBehavior(UUID userId) {


        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);


        String[] contexts = {"bored", "stress", "tired", "social", "screen-time"};


        for (String ctx : contexts) {


            long count = foodLogRepository
                    .countByUserIdAndContextAndTimestampAfter(userId, ctx, sevenDaysAgo);


            if (count >= 3) {
                return new ContextResult(ctx, (int) count);
            }
        }


        return null;
    }


    // ---------------- INNER CLASSES ----------------


    private static class TimeResult {
        String label;
        int count;


        TimeResult(String label, int count) {
            this.label = label;
            this.count = count;
        }
    }


    private static class ContextResult {
        String label;
        int count;


        ContextResult(String label, int count) {
            this.label = label;
            this.count = count;
        }
    }
}


