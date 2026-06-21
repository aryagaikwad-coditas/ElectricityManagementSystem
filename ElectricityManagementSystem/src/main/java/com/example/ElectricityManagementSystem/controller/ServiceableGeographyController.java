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






























/*
@RestController
@RequestMapping("/api/tenant/serviceable")
@RequiredArgsConstructor
public class ServiceableGeographyController {

    private final ServiceableGeographyService serviceableGeographyService;

    // ── STATES ──────────────────────────────────────────

    @PostMapping("/states")
    public ResponseEntity<String> addServiceableState(
            @RequestBody AddServiceableRequest req) {
        serviceableGeographyService.addServiceableState(req.getReferenceId());
        return ResponseEntity.ok("State marked as serviceable");
    }

    @DeleteMapping("/states/{stateId}")
    public ResponseEntity<String> removeServiceableState(
            @PathVariable Long stateId) {
        serviceableGeographyService.removeServiceableState(stateId);
        return ResponseEntity.ok("State removed from serviceable list");
    }

    @GetMapping("/states")
    public ResponseEntity<List<Map<String, Object>>> getServiceableStates() {
        return ResponseEntity.ok(
            serviceableGeographyService.getServiceableStates());
    }

    // ── DISTRICTS ────────────────────────────────────────

    @PostMapping("/districts")
    public ResponseEntity<String> addServiceableDistrict(
            @RequestBody AddServiceableRequest req) {
        serviceableGeographyService.addServiceableDistrict(req.getReferenceId());
        return ResponseEntity.ok("District marked as serviceable");
    }

    @DeleteMapping("/districts/{districtId}")
    public ResponseEntity<String> removeServiceableDistrict(
            @PathVariable Long districtId) {
        serviceableGeographyService.removeServiceableDistrict(districtId);
        return ResponseEntity.ok("District removed from serviceable list");
    }

    @GetMapping("/districts")
    public ResponseEntity<List<Map<String, Object>>> getServiceableDistricts(
            @RequestParam Long stateId) {
        return ResponseEntity.ok(
            serviceableGeographyService.getServiceableDistricts(stateId));
    }

    // ── CITIES ───────────────────────────────────────────

    @PostMapping("/cities")
    public ResponseEntity<String> addServiceableCity(
            @RequestBody AddServiceableRequest req) {
        serviceableGeographyService.addServiceableCity(req.getReferenceId());
        return ResponseEntity.ok("City marked as serviceable");
    }

    @DeleteMapping("/cities/{cityId}")
    public ResponseEntity<String> removeServiceableCity(
            @PathVariable Long cityId) {
        serviceableGeographyService.removeServiceableCity(cityId);
        return ResponseEntity.ok("City removed from serviceable list");
    }

    @GetMapping("/cities")
    public ResponseEntity<List<Map<String, Object>>> getServiceableCities(
            @RequestParam Long districtId) {
        return ResponseEntity.ok(
            serviceableGeographyService.getServiceableCities(districtId));
    }

    // ── AREAS ────────────────────────────────────────────

    @PostMapping("/areas")
    public ResponseEntity<String> addServiceableArea(
            @RequestBody AddServiceableRequest req) {
        serviceableGeographyService.addServiceableArea(req.getReferenceId());
        return ResponseEntity.ok("Area marked as serviceable");
    }

    @DeleteMapping("/areas/{areaId}")
    public ResponseEntity<String> removeServiceableArea(
            @PathVariable Long areaId) {
        serviceableGeographyService.removeServiceableArea(areaId);
        return ResponseEntity.ok("Area removed from serviceable list");
    }

    @GetMapping("/areas")
    public ResponseEntity<List<Map<String, Object>>> getServiceableAreas(
            @RequestParam Long cityId) {
        return ResponseEntity.ok(
            serviceableGeographyService.getServiceableAreas(cityId));
    }

    // ── BPO GEOGRAPHIC ASSIGNMENT ────────────────────────

    @PostMapping("/bpo-assignment")
    public ResponseEntity<String> assignBpo(
            @RequestBody BpoAssignRequest req) {
        serviceableGeographyService.assignBpoToGeography(
            req.getTenantUserId(), req.getRole(),
            req.getStateId(), req.getDistrictId(), req.getCityId()
        );
        return ResponseEntity.ok("BPO user assigned to geography");
    }

    @GetMapping("/bpo-assignment/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getBpoAssignments(
            @PathVariable Long userId) {
        return ResponseEntity.ok(
            serviceableGeographyService.getBpoAssignments(userId));
    }

    @Getter @Setter
    static class BpoAssignRequest {
        private Long tenantUserId;
        private String role;
        private Long stateId;
        private Long districtId;
        private Long cityId;
    }
}
 */