package com.lifeos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeos.entity.EnergySnapshot;

import java.util.UUID;

public interface EnergyRepository extends JpaRepository<EnergySnapshot, UUID> {
}
