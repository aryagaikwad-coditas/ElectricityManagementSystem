package com.example.ElectricityManagementSystem.repository.master;

import com.example.ElectricityManagementSystem.entity.master.CityHeadAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityHeadAssignmentRepository extends JpaRepository<CityHeadAssignment,Long> {
    List<CityHeadAssignment> findByUserIdAndActiveTrue(Long userId);
    List<CityHeadAssignment> findByCityIdAndActiveTrue(Long cityId);
}
