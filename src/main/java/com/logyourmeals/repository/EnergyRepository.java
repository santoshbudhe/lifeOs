package com.logyourmeals.repository;

import com.logyourmeals.entity.EnergySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnergyRepository extends JpaRepository<EnergySnapshot, UUID> {
}
