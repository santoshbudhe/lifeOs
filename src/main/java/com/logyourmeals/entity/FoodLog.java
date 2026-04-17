package com.logyourmeals.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "food_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodLog {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    @Column(columnDefinition = "TEXT")
    private String foodText;

    private Integer calories;

    private String context;

    private LocalDateTime timestamp;
}
