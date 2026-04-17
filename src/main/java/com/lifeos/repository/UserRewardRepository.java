package com.lifeos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeos.entity.UserReward;

import java.util.Optional;
import java.util.UUID;

public interface UserRewardRepository extends JpaRepository<UserReward, UUID> {

    Optional<UserReward> findByUserId(UUID userId);
}
