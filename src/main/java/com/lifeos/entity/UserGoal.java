package com.lifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGoal {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private String goalType; // WEIGHT_LOSS, MAINTENANCE, MUSCLE_GAIN, AWARENESS

    private int targetCalories;

    private LocalDateTime createdAt;
}
