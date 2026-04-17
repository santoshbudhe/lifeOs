package com.logyourmeals.repository;

import com.logyourmeals.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, UUID> {

    List<ActivityLog> findByUserIdAndTimestampBetween(
            UUID userId,
            LocalDateTime start,
            LocalDateTime end
    );
}
