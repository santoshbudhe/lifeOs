package com.logyourmeals.repository;

import com.logyourmeals.entity.InteractionFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InteractionFeedbackRepository extends JpaRepository<InteractionFeedback, UUID> {
}

