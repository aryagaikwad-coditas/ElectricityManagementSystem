package com.example.ElectricityManagementSystem.controller;

import com.example.ElectricityManagementSystem.dto.request.AssignTechnicianRequest;
import com.example.ElectricityManagementSystem.dto.response.ApiResponse;
import com.example.ElectricityManagementSystem.dto.response.AssignTechnicianResponse;
import com.example.ElectricityManagementSystem.dto.response.AvailableTechnicianResponse;
import com.example.ElectricityManagementSystem.service.TechnicianAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internal/technician")
@RequiredArgsConstructor
public class TechnicianAssignmentController {
    private final TechnicianAssignmentService technicianAssignmentService;

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<AvailableTechnicianResponse>>> getAvailableTenants(@RequestParam Long areaId) {
        return ResponseEntity.ok(ApiResponse.success("Fetched all the available technicians in the area", technicianAssignmentService.getAvailableTechnician(areaId)));
    }

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<AssignTechnicianResponse>>  assignTechnician(@RequestBody AssignTechnicianRequest request){
        return ResponseEntity.ok(ApiResponse.success("Assigning  the technician for the complaint request",technicianAssignmentService.assignTechnicianToComplaint(request)));
    }
}
