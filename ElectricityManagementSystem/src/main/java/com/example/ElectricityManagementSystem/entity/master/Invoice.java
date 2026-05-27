package com.example.ElectricityManagementSystem.entity.master;

import com.example.ElectricityManagementSystem.enums.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "invoices",schema = "master")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate billMonth;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private LocalDate paidDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private InvoiceStatus status =  InvoiceStatus.PENDING;

    private String remarks;

    @Column(nullable = false,name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt =  LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raised_by")
    private Users raiseBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="client_id")
    private Client client;
}
