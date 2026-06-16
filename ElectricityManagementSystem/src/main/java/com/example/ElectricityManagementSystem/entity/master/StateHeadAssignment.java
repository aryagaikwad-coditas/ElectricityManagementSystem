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
@Table(name = "state_head_assignment",schema = "master")
public class StateHeadAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "state_id",nullable = false)
    private State state;

    @Builder.Default
    @Column(nullable = false)
    private boolean active = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
