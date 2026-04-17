package com.logyourmeals.repository;

import com.logyourmeals.entity.UserReward;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRewardRepository extends JpaRepository<UserReward, UUID> {

    Optional<UserReward> findByUserId(UUID userId);
}
