package com.lifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "weight_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeightLog {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private Double weight;

    private LocalDate date;

    private LocalDateTime loggedAt;

    private LocalDateTime measuredAt;
}
