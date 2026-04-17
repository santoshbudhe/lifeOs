package com.lifeos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeos.entity.InteractionFeedback;

import java.util.UUID;

public interface InteractionFeedbackRepository extends JpaRepository<InteractionFeedback, UUID> {
}

