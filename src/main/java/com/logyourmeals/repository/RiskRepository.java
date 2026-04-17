package com.logyourmeals.repository;

import com.logyourmeals.entity.RiskProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RiskRepository extends JpaRepository<RiskProfile, UUID> {
}
