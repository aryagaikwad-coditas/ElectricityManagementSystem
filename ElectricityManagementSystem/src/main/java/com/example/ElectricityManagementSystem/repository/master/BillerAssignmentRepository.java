package com.example.ElectricityManagementSystem.repository.master;


import com.example.ElectricityManagementSystem.entity.master.BillerAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillerAssignmentRepository extends JpaRepository<BillerAssignment,Long> {
    List<BillerAssignment> findByCityIdAndActiveTrue(Long cityId);
}
