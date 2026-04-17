package com.logyourmeals.repository;

import com.logyourmeals.entity.WeightLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface WeightLogRepository extends JpaRepository<WeightLog, UUID> {

    List<WeightLog> findByUserId(UUID userId);

    List<WeightLog> findByUserIdAndDateBetween(UUID userId, LocalDate start, LocalDate end);
}