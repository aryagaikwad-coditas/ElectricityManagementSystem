package com.example.ElectricityManagementSystem.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignStateHeadRequest {
    private Long userId;
    private Long stateId;
}
