package com.example.ElectricityManagementSystem.repository.master;

import com.example.ElectricityManagementSystem.entity.master.StateHeadAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StateHeadassignmentRepository extends JpaRepository<StateHeadAssignment,Long> {
    List<StateHeadAssignment> findByStateIdAndActiveTrue(Long stateId);
    List<StateHeadAssignment> findByUserIdAndActiveTrue(Long userId);
}
