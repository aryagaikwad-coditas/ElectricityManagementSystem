package com.example.ElectricityManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignTechnicianResponse {
    private boolean success;
    private Long technicianId;
    private String technicianName;
    private String technicianPhone;
    private String message;
}
