package com.logyourmeals.repository;

import com.logyourmeals.entity.RoutineLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoutineRepository extends JpaRepository<RoutineLog, UUID> {

    List<RoutineLog> findByUserId(UUID userId);

}
