package com.example.ElectricityManagementSystem.controller;

import com.example.ElectricityManagementSystem.dto.request.AddServiceableRequest;
import com.example.ElectricityManagementSystem.service.ServiceableGeographyService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tenant/serviceable")
public class ServiceableGeographyController {

    private final ServiceableGeographyService serviceableGeographyService;

    @PostMapping("/states")
    public ResponseEntity<String> addServiceableStates(@RequestBody AddServiceableRequest req){
        serviceableGeographyService.addServiceableStates(req.getReferenceId());
        return ResponseEntity.ok("Successfully added serviceable states");
    }

    @DeleteMapping("/state/{stateId}")
    public ResponseEntity<String> removeServiceableStates(@PathVariable Long stateId){
        serviceableGeographyService.removeServiceableStates(stateId);
        return ResponseEntity.ok("Successfully removed serviceable states");
    }

    @PostMapping("/district")
    public ResponseEntity<String> addServiceableDistrict(@RequestBody AddServiceableRequest req){
        serviceableGeographyService.addServiceableDistrict(req.getReferenceId());
        return ResponseEntity.ok("Successfully added serviceable district");
    }

    @DeleteMapping("/district/{districtId}")
    public ResponseEntity<String> removeServiceableDistrict(@PathVariable Long districtId){
        serviceableGeographyService.removeServiceableDistrict(districtId);
        return ResponseEntity.ok("Successfully removed serviceable district");
    }

    @PostMapping("/city")
    public ResponseEntity<String> addServiceableCity(@RequestBody AddServiceableRequest req){
        serviceableGeographyService.addServiceableCity(req.getReferenceId());
        return ResponseEntity.ok("Successfully added serviceable city");
    }

    @DeleteMapping("/city/{cityId}")
    public ResponseEntity<String> removeServiceableCity(@PathVariable Long cityId){
        serviceableGeographyService.removeServiceableCity(cityId);
        return ResponseEntity.ok("Successfully removed serviceable city");
    }

    @PostMapping("/area")
    public ResponseEntity<String> addServiceableArea(@RequestBody AddServiceableRequest req){
        serviceableGeographyService.addServiceableArea(req.getReferenceId());
        return ResponseEntity.ok("Successfully added serviceable area");
    }

    @DeleteMapping("/area/{areaId}")
    public ResponseEntity<String> removeServiceableArea(@PathVariable Long areaId){
        serviceableGeographyService.removeServiceableArea(areaId);
        return ResponseEntity.ok("Area removed from serviceable part");
    }




}
