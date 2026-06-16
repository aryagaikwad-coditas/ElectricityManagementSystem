package com.example.ElectricityManagementSystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignBillerRequest {
    private Long billerId;
    private Long consumerId;
    private Long cityId;
    private String tenantSchema;
}
