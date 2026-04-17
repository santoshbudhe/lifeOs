package com.lifeos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeos.entity.RiskProfile;

import java.util.UUID;

public interface RiskRepository extends JpaRepository<RiskProfile, UUID> {
}
