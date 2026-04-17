package com.lifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "user_rewards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReward {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private int streakDays;

    private int score; // 0–100 or more

    private LocalDate lastActiveDate;
}
