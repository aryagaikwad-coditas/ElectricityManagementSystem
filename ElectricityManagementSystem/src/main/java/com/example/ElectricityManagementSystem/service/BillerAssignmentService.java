package com.example.ElectricityManagementSystem.service;

import com.example.ElectricityManagementSystem.dto.request.AssignBillerRequest;
import com.example.ElectricityManagementSystem.dto.response.AssignTechnicianResponse;
import com.example.ElectricityManagementSystem.dto.response.AvailableBillerResponse;
import com.example.ElectricityManagementSystem.entity.master.BillerAssignment;
import com.example.ElectricityManagementSystem.repository.master.BillerAssignmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillerAssignmentService {
    private final BillerAssignmentRepository billerAssignmentRepository;
    private final RestTemplate restTemplate;

    public List<AvailableBillerResponse> getAvailableBillers(Long cityId){
        List<BillerAssignment> assignments = billerAssignmentRepository.findByCityIdAndActiveTrue(cityId);

        return assignments.stream().map(a-> AvailableBillerResponse.builder()
                .billerId(a.getBiller().getId())
                .fullName(a.getBiller().getFullName())
                .email(a.getBiller().getEmail())
                .phone(a.getBiller().getPhone())
                .cityId(a.getCity().getId())
                .cityName(a.getCity().getName())
                .build()
        ).collect(Collectors.toList());
    }

    public AssignTechnicianResponse assignBillerToConsumer(AssignBillerRequest req) {
        boolean valid = billerAssignmentRepository
                .findByCityIdAndActiveTrue(req.getCityId())
                .stream()
                .anyMatch(a -> a.getBiller().getId().equals(req.getBillerId()));

        if (!valid) {
            return AssignTechnicianResponse.builder()
                    .success(false)
                    .message("BillerAssignment not found")
                    .build();}

            var biller = billerAssignmentRepository.findByCityIdAndActiveTrue(req.getCityId())
                    .stream()
                    .filter(a -> a.getBiller().getId().equals(req.getBillerId()))
                    .findFirst()
                    .orElseThrow()
                    .getBiller();

            String url = "http://localhost:8080/api/tenant/internal/consumers/"
                    + req.getConsumerId() + "/assign-biller";

            TenantBillerUpdateRequest updateReq = new TenantBillerUpdateRequest(
                    req.getConsumerId(),
                    biller.getId(),
                    biller.getFullName(),
                    biller.getPhone()
            );

            restTemplate.postForEntity(url, updateReq, Void.class);

            return AssignTechnicianResponse.builder()
                    .success(true)
                    .technicianId(biller.getId())
                    .technicianName(biller.getFullName())
                    .technicianPhone(biller.getPhone())
                    .message("Biller assigned successfully")
                    .build();

    }

        private record TenantBillerUpdateRequest(
                Long consumerId,
                Long billerId,
                String billerName,
                String billerPhone
        ) {}
}
