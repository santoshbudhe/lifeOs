package com.lifeos.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "energy_snapshots")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergySnapshot {

    @Id
    @GeneratedValue
    private UUID id;

    private UUID userId;

    private int energyScore; // 0–100

    private String energyLevel; // HIGH / MEDIUM / LOW

    private LocalDateTime timestamp;
}
