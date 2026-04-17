package com.lifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "interaction_feedback")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteractionFeedback {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private String messageType;
    // NUDGE / FOLLOW_UP / INSIGHT

    @Column(length = 1000)
    private String messageText;

    private String timeWindow;
    // MORNING / LUNCH / EVENING

    private String userType;
    // LOW / MID / HIGH

    private String feedback;
    // UP / DOWN

    private LocalDateTime timestamp;
}

