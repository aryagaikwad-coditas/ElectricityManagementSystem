package com.example.ElectricityManagementSystem.controller;

import com.example.ElectricityManagementSystem.dto.request.*;
import com.example.ElectricityManagementSystem.dto.response.ApiResponse;
import com.example.ElectricityManagementSystem.service.HeadAssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
@RequiredArgsConstructor
public class HeadAssignmentController {
    private final HeadAssignmentService headAssignmentService;

    //     --------------- STATE HEAD ASSIGNMENT APIS ----------------------
    @PostMapping("/state-head")
    @PreAuthorize("hasRole('MANAGEMENT')")
    public ResponseEntity<String> assignStateHead(@RequestBody AssignStateHeadRequest req){
        headAssignmentService.assignStateHead(req);
        return ResponseEntity.ok("Successfully assigned state head");
    }

    @GetMapping("/state-head/by-state/{stateId}")
    public ResponseEntity<?> getStateHeadByState(@PathVariable Long stateId){
        headAssignmentService.getStateHeadByState(stateId);
        return ResponseEntity.ok("Successfully fetched state head based on stateId");
    }

    @GetMapping("/state-head/by-user/{userId}")
    public ResponseEntity<?> getStateByStateHead(@PathVariable Long userId){
        headAssignmentService.getStateByStateHead(userId);
        return ResponseEntity.ok("Successfully fetched state head based on stateId");
    }

    @DeleteMapping("/state-head")
    @PreAuthorize("hasRole('MANAGEMENT')")
    public ResponseEntity<String> removeStateHead(@PathVariable Long userId,@PathVariable Long stateId){
        headAssignmentService.removeStateHead(userId,stateId);
        return ResponseEntity.ok("Successfully removed state head");
    }

    // --------------- District head Assignment -----------------------

    @PostMapping("/district-head")
    @PreAuthorize("hasAnyRole('MANAGEMENT','STATE_HEAD')")
    public ResponseEntity<String> assignDistrictHead(@RequestBody AssignDistrictHeadRequest req){
        headAssignmentService.assignDistrict(req);
        return ResponseEntity.ok("Successfully assigned district head");
    }

    @GetMapping("/district-head/by-district/{districtId}")
    public ResponseEntity<?> getDistrictHeadsByDistrict(@PathVariable Long districtId){
        headAssignmentService.getDistrictByDistrictId(districtId);
        return ResponseEntity.ok("Successfully fetched district head");
    }

    @GetMapping("/district-head/by-user/{userId}")
    public ResponseEntity<?> getDistrictsByDistrictHead(@PathVariable Long userId){
        headAssignmentService.getDistrictByDistrictHead(userId);
        return ResponseEntity.ok("Successfully fetched district head");
    }

    @DeleteMapping("/distrci-head")
    @PreAuthorize("hasAnyRole('MANAGEMENT','STATE_HEAD')")
    public ResponseEntity<String> removeDistrictHead(@PathVariable Long userId,@PathVariable Long districtId){
        headAssignmentService.removeDistrictHead(userId,districtId);
        return ResponseEntity.ok("Successfully removed state head");
    }

    // -----------------  CITY API'S ----------------

    @PostMapping("/city-head")
    @PreAuthorize("hasAnyRole('MANAGEMENT','STATE_HEAD','DISTRICT_HEAD')")
    public ResponseEntity<String >assignCityHead(@RequestBody AssignCityHeadRequest req){
        headAssignmentService.assignCityHead(req);
        return ResponseEntity.ok("Successfully assigned city head");
    }

    @GetMapping("/city-head/by-city/{cityId}")
    public ResponseEntity<?> getCityHeadsByCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(
                headAssignmentService.getCityHeadsByCity(cityId));
    }


    @GetMapping("/city-head/by-user/{userId}")
    public ResponseEntity<?> getCitiesByCityHead(@PathVariable Long userId) {
        return ResponseEntity.ok(
                headAssignmentService.getCitiesByCityHead(userId));
    }


    // ------------ Technician API'S----------------

    @PostMapping("/technician-head")
    @PreAuthorize("hasAnyRole('STATE_HEAD','DISTRICT_HEAD','CITY_HEAD')")
    public ResponseEntity<String> assignTechnicianToArea(@RequestBody AssignTechnicianRequest req){
        headAssignmentService.assignTechnicianToArea(req);
        return ResponseEntity.ok("Successfully assigned technician to area");
    }

    @GetMapping("technician-head/by-area/{areaId}")
    public ResponseEntity<?> getTechnicianHeadsByArea(@PathVariable Long areaId) {
        return ResponseEntity.ok(headAssignmentService.getTechnicianHeadsByArea(areaId));
    }

    // ------------------------  Biller API'S -----------------------

    @PostMapping("/biller-city")
    @PreAuthorize("hasAnyRole('CITY_HEAD','DISTRICT_HEAD')")
    public ResponseEntity<String> assignBillerToCity(@RequestBody AssignBillerRequest req){
        headAssignmentService.assignBillerToCity(req);
        return ResponseEntity.ok("Successfully assigned billers to city");
    }

    @GetMapping("/biller-city/by-city/{cityId}")
    public ResponseEntity<?> getBillerByCity(@PathVariable Long cityId) {
        headAssignmentService.getBillersByCity(cityId);
        return ResponseEntity.ok("Successfully assigned billers to city");
    }

}























