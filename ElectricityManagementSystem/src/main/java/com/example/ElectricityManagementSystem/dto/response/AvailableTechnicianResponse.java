package com.example.ElectricityManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableTechnicianResponse {
    private Long technicianId;
    private String fullName;
    private String email;
    private String phone;
    private String areaName;
    private Long areaId;
}
