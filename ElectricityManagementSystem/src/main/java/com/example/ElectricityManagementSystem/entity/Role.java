package com.example.ElectricityManagementSystem.entity;

import com.example.ElectricityManagementSystem.enums.RoleType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NegativeOrZero;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "roles",schema = "master")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType name;
}
