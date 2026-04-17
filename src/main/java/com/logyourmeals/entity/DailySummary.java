package com.logyourmeals.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "daily_summary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySummary {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private LocalDate date;

    private Integer totalCalories;

    private Integer targetCalories;

    private Integer calorieDelta; // total - target
}
