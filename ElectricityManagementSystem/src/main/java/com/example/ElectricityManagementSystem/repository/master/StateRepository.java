package com.example.ElectricityManagementSystem.repository.master;


import com.example.ElectricityManagementSystem.entity.master.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {
}
