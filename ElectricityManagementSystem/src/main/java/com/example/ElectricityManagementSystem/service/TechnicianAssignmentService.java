package com.example.ElectricityManagementSystem.service;

import com.example.ElectricityManagementSystem.dto.request.AssignTechnicianRequest;
import com.example.ElectricityManagementSystem.dto.response.AssignTechnicianResponse;
import com.example.ElectricityManagementSystem.dto.response.AvailableTechnicianResponse;
import com.example.ElectricityManagementSystem.entity.master.TechnicianAreaAssignment;
import com.example.ElectricityManagementSystem.entity.master.Users;
import com.example.ElectricityManagementSystem.repository.master.TechnicianAreaAssignmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TechnicianAssignmentService {

    private final TechnicianAreaAssignmentRepository technicianAreaAssignmentRepository;
    private final RestTemplate restTemplate;
    public List<AvailableTechnicianResponse> getAvailableTechnician(Long areaId) {

        List<TechnicianAreaAssignment> assignments =
                technicianAreaAssignmentRepository.findByAreaIdAndActiveTrue(areaId);

        return assignments.stream().map(
                a -> AvailableTechnicianResponse
                        .builder()
                        .technicianId(a.getId())
                        .fullName(a.getUser().getFullName())
                        .phone(a.getUser().getPhone())
                        .email(a.getUser().getEmail())
                        .areaName(a.getArea().getName())
                        .areaId(a.getArea().getId())
                        .build())
                .collect(Collectors.toList());
    }

    public AssignTechnicianResponse assignTechnicianToComplaint(AssignTechnicianRequest request) {

        //validating if the technician belongs to that particular area or not
        boolean valid = technicianAreaAssignmentRepository
                .findByAreaIdAndActiveTrue(request.getAreaId())
                .stream().anyMatch(a-> a.getUser().getId()
                        .equals(request.getTechnicianId()));

        if(!valid){
            return AssignTechnicianResponse.builder()
                    .success(false)
                    .message("Technician does not belong to this area at all")
                    .build();
        }

        TechnicianAreaAssignment assignment = technicianAreaAssignmentRepository
                .findByAreaIdAndActiveTrue(request.getAreaId())
                .stream()
                .filter(a-> a.getUser().getId().equals(request.getTechnicianId()))
                .findFirst().orElseThrow();

        var technician = assignment.getUser();

        String url = "http://localhost:8080/api/tenant/internal/complaints/"
                + request.getComplainId() + "/assign-technician";

        TenantComplaintUpdateRequest updateReq =
                new TenantComplaintUpdateRequest(
                        request.getComplainId(),
                        technician.getId(),
                        technician.getFullName(),
                        technician.getPhone()
                );
        restTemplate.postForEntity(url, updateReq, Void.class);

        return AssignTechnicianResponse.builder()
                .success(true)
                .technicianId(technician.getId())
                .technicianName(technician.getFullName())
                .technicianPhone(technician.getPhone())
                .message("Successfully fetched the user we want ").build();

    }
    private record TenantComplaintUpdateRequest(
            Long complaintId,
            Long technicianId,
            String technicianName,
            String technicianPhone
    ) {}
}
