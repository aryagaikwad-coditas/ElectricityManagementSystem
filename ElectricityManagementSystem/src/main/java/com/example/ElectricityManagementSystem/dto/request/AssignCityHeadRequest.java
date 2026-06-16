package com.example.ElectricityManagementSystem.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignCityHeadRequest {
    private Long userId;
    private Long cityId;
}
