package com.lifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "routine_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineLog {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private String routineType; // MORNING, AFTERNOON, EVENING, NIGHT

    private int hourOfDay;

    private LocalDateTime timestamp;
}

