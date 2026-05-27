package com.example.ElectricityManagementSystem.repository.master;

import com.example.ElectricityManagementSystem.entity.master.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City,Long> {
}
