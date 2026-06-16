package com.example.ElectricityManagementSystem.dto.request;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignTechnicianRequest {
    private Long technicianId;
    private Long areaId;
    private Long complainId;
    private String tenantSchema;
}
