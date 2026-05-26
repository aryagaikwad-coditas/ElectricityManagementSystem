package com.example.ElectricityManagementSystem.repository;

import com.example.ElectricityManagementSystem.entity.Role;
import com.example.ElectricityManagementSystem.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
