package com.example.ElectricityManagementSystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AvailableBillerResponse {
    private Long billerId;
    private String fullName;
    private String phone;
    private String email;
    private Long cityId;
    private String cityName;
}
