package com.lifeos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeos.entity.RoutineLog;

import java.util.List;
import java.util.UUID;

public interface RoutineRepository extends JpaRepository<RoutineLog, UUID> {

    List<RoutineLog> findByUserId(UUID userId);

}
