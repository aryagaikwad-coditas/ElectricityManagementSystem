package com.example.ElectricityManagementSystem.controller;

import com.example.ElectricityManagementSystem.dto.request.AssignBillerRequest;
import com.example.ElectricityManagementSystem.dto.response.ApiResponse;
import com.example.ElectricityManagementSystem.dto.response.AssignTechnicianResponse;
import com.example.ElectricityManagementSystem.dto.response.AvailableBillerResponse;
import com.example.ElectricityManagementSystem.service.BillerAssignmentService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/internl/billers")
@RequiredArgsConstructor
public class BillerAssignmentController {
    private final BillerAssignmentService billerAssignmentService;

    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<AvailableBillerResponse>>> getAvailableBiller(@RequestParam Long cityId){
        return ResponseEntity.ok(ApiResponse.success("Fetching all the available billers ", getAvailableBiller(cityId)));
    }

    @PostMapping("/assign")
    public ResponseEntity<ApiResponse<AssignTechnicianResponse>> assign(@RequestBody AssignBillerRequest req){
        return ResponseEntity.ok(ApiResponse.success("Assigning a biller to a consumer",billerAssignmentService.assignBillerToConsumer(req)));
    }
}
