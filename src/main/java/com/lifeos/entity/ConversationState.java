package com.lifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "conversation_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConversationState {

    @Id
    private UUID userId;

    private int questionsAskedToday;

    private LocalDateTime lastQuestionTime;

    private LocalDateTime lastNudgeTime;

    private LocalDateTime lastFollowUpTime;

    private LocalDateTime lastMessageTime;

    private LocalDateTime lastResetDate;
}
