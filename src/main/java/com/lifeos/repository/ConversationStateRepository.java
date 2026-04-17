package com.lifeos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lifeos.entity.ConversationState;

import java.util.UUID;

public interface ConversationStateRepository extends JpaRepository<ConversationState, UUID> {
}
