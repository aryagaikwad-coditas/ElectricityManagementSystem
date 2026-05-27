package com.example.ElectricityManagementSystem.repository.master;


import com.example.ElectricityManagementSystem.entity.master.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State,Long> {
}
