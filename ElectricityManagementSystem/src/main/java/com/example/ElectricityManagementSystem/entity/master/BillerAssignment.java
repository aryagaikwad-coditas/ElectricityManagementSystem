package com.example.ElectricityManagementSystem.entity.master;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="biller_assignment",schema = "master")
public class BillerAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="biller_id")
    private Users biller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id",nullable = false)
    private City city;

    @Column(nullable = false)
    @Builder.Default
    private boolean active = true;

    @Builder.Default
    @Column(nullable = false,name = "assigned_at")
    private LocalDateTime assignedAt = LocalDateTime.now();
}
