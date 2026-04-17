package com.logyourmeals.repository;

import com.logyourmeals.entity.ConversationState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConversationStateRepository extends JpaRepository<ConversationState, UUID> {
}
