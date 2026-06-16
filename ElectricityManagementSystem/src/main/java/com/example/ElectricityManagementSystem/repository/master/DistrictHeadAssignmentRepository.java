package com.example.ElectricityManagementSystem.repository.master;

import com.example.ElectricityManagementSystem.entity.master.DistrictHeadAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DistrictHeadAssignmentRepository extends JpaRepository<DistrictHeadAssignment,Long> {
    List<DistrictHeadAssignment> findByUserIdAndActiveTrue(Long userId);
    List<DistrictHeadAssignment> findByDistrictIdAndActiveTrue(Long districtId);
}
