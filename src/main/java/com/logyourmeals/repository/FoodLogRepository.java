package com.logyourmeals.repository;


import com.logyourmeals.entity.FoodLog;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


public interface FoodLogRepository extends JpaRepository<FoodLog, UUID> {


    List<FoodLog> findByUserId(UUID userId);


    // ✅ ADD THIS
    long countByUserIdAndTimestampAfter(UUID userId, LocalDateTime time);


    // ✅ ALSO THIS (for context detection)
    long countByUserIdAndContextAndTimestampAfter(UUID userId, String context, LocalDateTime time);

    List<FoodLog> findByUserIdAndTimestampAfter(UUID userId, LocalDateTime timestamp);

}


