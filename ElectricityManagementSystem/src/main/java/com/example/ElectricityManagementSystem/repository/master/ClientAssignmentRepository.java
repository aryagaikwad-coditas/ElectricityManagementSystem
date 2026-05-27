package com.example.ElectricityManagementSystem.repository.master;

import com.example.ElectricityManagementSystem.entity.master.ClientAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientAssignmentRepository extends JpaRepository<ClientAssignment,Long> {
}
