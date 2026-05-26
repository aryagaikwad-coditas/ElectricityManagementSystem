package com.example.ElectricityManagementSystem.entity;

import com.example.ElectricityManagementSystem.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "clients",schema = "master")
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String schemaName;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String address;

    private String ClientPocName;
    private String ClientPocPhone;
    private String ClientPocEmail;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClientStatus status = ClientStatus.ACTIVE;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarded_by")
    private Users onboarded_by;

    @Column(nullable = false)
    private LocalDateTime created = LocalDateTime.now();

}
