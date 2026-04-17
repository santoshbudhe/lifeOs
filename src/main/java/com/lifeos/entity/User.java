package com.lifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private UUID id;

    private String phoneNumber;

    private Integer dailyCalorieTarget;
    
    private Integer weight, height, age, bmr;
    
    private String gender;
    
    private String name;

    private String email;

    private Integer targetCalories;

    // 🔥 NEW FIELDS

    private String userType;
    // LOW / MID / HIGH

    private String engagementLevel;
    // LOW / MEDIUM / HIGH

    private String preferredNudgeWindow;
    // MORNING_WALK / LUNCH / EVENING

    private LocalDateTime createdAt;
    	 
    
}