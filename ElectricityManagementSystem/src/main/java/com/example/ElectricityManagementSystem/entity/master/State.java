package com.example.ElectricityManagementSystem.entity.master;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "state",schema = "master")
@Builder
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable=false)
    private String code; // eg gujarat -> gj kiva maharashtra -> mh
}
