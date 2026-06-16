package com.example.ElectricityManagementSystem.controller;

import com.example.ElectricityManagementSystem.dto.response.ApiResponse;
import com.example.ElectricityManagementSystem.dto.response.AvailableTechnicianResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/tenant/complaints")
@RequiredArgsConstructor
public class TenantComplaintController {

    private final JdbcTemplate jdbcTemplate;
    private final RestTemplate restTemplate;

    @PostMapping("/{complaintId}/available-technician")
    public ResponseEntity<ApiResponse<AvailableTechnicianResponse>> getAvailableTechnician(@PathVariable Long complaintId){
        Long areaId = jdbcTemplate.queryForObject(
                "SELECT c.area_id FROM complaints comp " +
                        "JOIN consumers c ON comp.consumer_id = c.id " +
                        "WHERE comp.id = ?",
                Long.class, complaintId
        );

        String url = "http://localhost:8080/api/internal/technician/available?areaId="+ areaId;

        ResponseEntity<AvailableTechnicianResponse[]> response =
                restTemplate.getForEntity(url, AvailableTechnicianResponse[].class);

        return ResponseEntity.ok((ApiResponse<AvailableTechnicianResponse>) List.of(
                response.getBody() != null ? response.getBody() : new AvailableTechnicianResponse[0]
        ));


    }
}
