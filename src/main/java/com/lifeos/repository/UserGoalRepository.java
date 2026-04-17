package com.lifeos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeos.entity.UserGoal;

import java.util.Optional;
import java.util.UUID;

public interface UserGoalRepository extends JpaRepository<UserGoal, UUID> {

    Optional<UserGoal> findByUserId(UUID userId);
}
