package com.example.ElectricityManagementSystem.controller;

import com.example.ElectricityManagementSystem.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant/internal/complaints")
@RequiredArgsConstructor
public class TenantComplainInternalController {
    private final JdbcTemplate jdbcTemplate;

    public record AssignTechnicianRequest(
            Long complaintId,
            Long technicianId,
            String technicianName,
            String technicianPhone
    ) {}

    @PostMapping("/{complainId}/assign-Technician")
    public ResponseEntity<String>  assignTechnician(@RequestParam Long complaintId, @RequestBody AssignTechnicianRequest request){
        jdbcTemplate.update(
                "UPDATE complaints SET " +
                        "assigned_technician_id = ?, " +
                        "status = 'ASSIGNED' " +
                        "WHERE id = ?",
                request.technicianId(), complaintId
        );

        return ResponseEntity.ok("Complaint updated with technician");
    }
}
