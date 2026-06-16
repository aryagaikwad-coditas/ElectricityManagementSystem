package com.example.ElectricityManagementSystem.repository.master;

import com.example.ElectricityManagementSystem.entity.master.TechnicianAreaAssignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechnicianAreaAssignmentRepository extends JpaRepository<TechnicianAreaAssignment,Long> {

    List<TechnicianAreaAssignment> findByAreaIdAndActiveTrue(Long areaId);
    List<TechnicianAreaAssignment> findByTechnicianIdAndActiveTrue(Long technicianId);
}
