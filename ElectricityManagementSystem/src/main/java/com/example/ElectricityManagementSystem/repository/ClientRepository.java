package com.example.ElectricityManagementSystem.repository;

import com.example.ElectricityManagementSystem.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
