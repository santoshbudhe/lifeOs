package com.logyourmeals.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "activity_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private String activityType; // walking, running, gym

    private Double distance; // in km (optional)

    private Integer duration; // in minutes (optional)

    private Integer caloriesBurned;

    private LocalDateTime timestamp;
}

