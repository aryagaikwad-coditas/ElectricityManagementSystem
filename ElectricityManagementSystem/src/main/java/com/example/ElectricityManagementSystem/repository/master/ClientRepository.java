package com.example.ElectricityManagementSystem.repository.master;

import com.example.ElectricityManagementSystem.entity.master.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
