package com.logyourmeals.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "risk_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskProfile {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private String riskType; // OVER_EATING, LOW_ENERGY, etc.

    private int riskScore; // 0–100

    private String message;

    private LocalDateTime timestamp;
}

