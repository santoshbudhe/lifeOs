package com.logyourmeals.repository;

import com.logyourmeals.entity.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DailySummaryRepository extends JpaRepository<DailySummary, UUID> {

    // Get summary for a specific day
    Optional<DailySummary> findByUserIdAndDate(UUID userId, LocalDate date);

    // Get all summaries for a user
    List<DailySummary> findByUserId(UUID userId);

    // Get summaries between dates (for weekly/monthly insights)
    List<DailySummary> findByUserIdAndDateBetween(UUID userId, LocalDate startDate, LocalDate endDate);

    // 🔥 Rolling calorie balance (sum of deltas)
    @Query("SELECT COALESCE(SUM(d.calorieDelta), 0) FROM DailySummary d " +
           "WHERE d.userId = :userId AND d.date BETWEEN :startDate AND :endDate")
    Integer getTotalCalorieDeltaBetweenDates(@Param("userId") UUID userId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);
}
