package com.example.ElectricityManagementSystem.service;

import com.example.ElectricityManagementSystem.dto.request.*;
import com.example.ElectricityManagementSystem.entity.master.*;
import com.example.ElectricityManagementSystem.enums.RoleType;
import com.example.ElectricityManagementSystem.repository.master.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HeadAssignmentService {

    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final AreaRepository areaRepository;
    private final DistrictRepository districtRepository;
    private final StateRepository stateRepository;
    private final StateHeadassignmentRepository  stateHeadassignmentRepository;
    private final DistrictHeadAssignmentRepository districtHeadAssignmentRepository;
    private final CityHeadAssignmentRepository cityHeadAssignmentRepository;
    private final TechnicianAreaAssignmentRepository  technicianAreaAssignmentRepository;
    private final BillerAssignmentRepository  billerAssignmentRepository;


    @Transactional
    public void assignStateHead(AssignStateHeadRequest req) {
        Users user = userRepository.findById(req.getUserId())
                        .orElseThrow(()-> new RuntimeException("User not found "));

        boolean hasRole = user.getRoles()
                .stream()
                .anyMatch(r-> r.getName().equals(RoleType.STATE_HEAD));

        if(!hasRole){
            throw new RuntimeException("User is not STATE_HEAD role");
        }

        State state = stateRepository
                .findById(req.getStateId())
                .orElseThrow(()-> new RuntimeException("State not found"));

        boolean exists = stateHeadassignmentRepository
                .findByStateIdAndActiveTrue(req.getStateId()).stream()
                .anyMatch(a->a.getUsers().getId().equals(user.getId()));

        if(exists){
            throw new RuntimeException("State Head has already been assigned for this particular state ");
        }

        StateHeadAssignment assignment = StateHeadAssignment.
                builder()
                .users(user)
                .state(state)
                .build();

        stateHeadassignmentRepository.save(assignment);
    }

    public List<StateHeadAssignment> getStateHeadByState(Long stateId) {
        return stateHeadassignmentRepository.findByStateIdAndActiveTrue(stateId);
    }

    public List<StateHeadAssignment> getStateByStateHead(Long userId) {
        return stateHeadassignmentRepository.findByUserIdAndActiveTrue(userId);
    }

    @Transactional
    public void removeStateHead(Long userId, Long stateId) {
        stateHeadassignmentRepository
                .findByStateIdAndActiveTrue(stateId)
                .stream()
                .filter(a -> a.getUsers().getId().equals(userId))
                .findFirst()
                .ifPresent(a -> {
                    a.setActive(false);
                    stateHeadassignmentRepository.save(a);
                });
    }

    public void assignDistrict(AssignDistrictHeadRequest req) {
        Users user = userRepository.
                findById(req.getUserId()).orElseThrow(()-> new RuntimeException("User not found "));

        boolean hasRole = user.getRoles().stream()
                .anyMatch(r-> r.getName().equals(RoleType.DISTRICT_HEAD) || r.getName().equals(RoleType.STATE_HEAD));

        if(!hasRole){
            throw new RuntimeException("User is not STATE_HEAD role or DISTRICT_HEAD role");
        }
        District district = districtRepository
                .findById(req.getDistrictId()).orElseThrow(()-> new RuntimeException("District not found"));

        boolean exists = districtHeadAssignmentRepository.findByDistrictIdAndActiveTrue(req.getDistrictId())
                .stream().anyMatch(r-> r.getUser().getId().equals(user.getId()));

        if(exists){
            throw new RuntimeException("User has already been assigned as a districtHead with the given districtId");
        }
        DistrictHeadAssignment assignment = DistrictHeadAssignment.builder()
                .district(district)
                .user(user)
                .build();
        districtHeadAssignmentRepository.save(assignment);
    }

    public List<DistrictHeadAssignment> getDistrictByDistrictId(Long districtId) {
        return districtHeadAssignmentRepository.findByDistrictIdAndActiveTrue(districtId);
    }

    public List<DistrictHeadAssignment> getDistrictByDistrictHead(Long userId) {
        return districtHeadAssignmentRepository.findByUserIdAndActiveTrue(userId);
    }

    @Transactional
    public void removeDistrictHead(Long userId, Long districtId) {

        districtHeadAssignmentRepository
                .findByDistrictIdAndActiveTrue(districtId)
                .stream()
                .filter(a->a.getUser().getId().equals(userId))
                .findFirst()
                .ifPresent(a -> {
                    a.setActive(false);
                    districtHeadAssignmentRepository.save(a);
                });
    }

    @Transactional
    public void assignCityHead(AssignCityHeadRequest req) {
        Users user = userRepository
                .findById(req.getUserId()).orElseThrow(()-> new RuntimeException("User not found"));

        boolean hasRole = user.getRoles()
                .stream()
                .anyMatch(a->a.getName().equals(RoleType.CITY_HEAD) || a.getName().equals(RoleType.DISTRICT_HEAD));

        if(!hasRole){
            throw new RuntimeException("User is not STATE_HEAD role or CITY_HEAD role");
        }

        City city = cityRepository
                .findById(req.getCityId()).orElseThrow(()-> new RuntimeException("City not found"));

        boolean valid = cityHeadAssignmentRepository
                .findByCityIdAndActiveTrue(req.getCityId())
                .stream().anyMatch(a->a.getUser().getId().equals(user.getId()));

        if(valid){
            throw new RuntimeException("User has already been assigned as a cityHead with the given cityId");
        }

        CityHeadAssignment assignment = CityHeadAssignment.builder()
                .user(user)
                .city(city)
                .build();
        cityHeadAssignmentRepository.save(assignment);
    }

    public List<CityHeadAssignment> getCityHeadsByCity(Long cityId) {
        return cityHeadAssignmentRepository.findByCityIdAndActiveTrue(cityId);
    }

    public List<CityHeadAssignment> getCitiesByCityHead(Long userId) {
        return cityHeadAssignmentRepository.findByUserIdAndActiveTrue(userId);
    }

    @Transactional
    public void assignTechnicianToArea(AssignTechnicianRequest req) {
        Users technician = userRepository
                .findById(req.getTechnicianId())
                .orElseThrow(()-> new RuntimeException("User not found"));

        boolean hasRole = technician.getRoles()
                .stream().anyMatch(a->a.getName().equals(RoleType.CITY_HEAD) || a.getName().equals(RoleType.DISTRICT_HEAD));

        if(!hasRole){
            throw new RuntimeException("User is not STATE_HEAD role or CITY_HEAD role");
        }
        Area area = areaRepository
                .findById(req.getAreaId())
                .orElseThrow(()-> new RuntimeException("Area not found"));

        boolean valid = technicianAreaAssignmentRepository
                .findByTechnicianIdAndActiveTrue(req.getTechnicianId())
                .stream()
                .anyMatch(a-> a.getUser().getId().equals(technician.getId()));

        if(valid){
            throw new RuntimeException("Technician has already been assigned to a particular area or complaint ");
        }

        TechnicianAreaAssignment assignment = TechnicianAreaAssignment.builder()
                .user(technician)
                .area(area)
                .build();

        technicianAreaAssignmentRepository.save(assignment);
    }

    public List<TechnicianAreaAssignment> getTechnicianHeadsByArea(Long areaId) {
        return technicianAreaAssignmentRepository.findByAreaIdAndActiveTrue(areaId);
    }

    @Transactional
    public void assignBillerToCity(AssignBillerRequest req) {
        Users biller = userRepository.findById(req.getBillerId())
                .orElseThrow(()-> new RuntimeException("User not found"));

        boolean hasRole = biller.getRoles()
                .stream().anyMatch(a->a.getName().equals(RoleType.CITY_HEAD) || a.getName().equals(RoleType.DISTRICT_HEAD));

        if(!hasRole){
            throw new RuntimeException("User is not a biller ");
        }

        City city = cityRepository.findById(req.getCityId())
                .orElseThrow(()-> new RuntimeException("City not found"));

        boolean valid = billerAssignmentRepository
                .findByCityIdAndActiveTrue(req.getCityId())
                .stream()
                .anyMatch(a->a.getBiller().getId().equals(biller.getId()));

        if(valid){
            throw new RuntimeException("Biller has already been added to the city ");
        }

        BillerAssignment assignment = BillerAssignment.builder()
                .biller(biller)
                .city(city)
                .build();

        billerAssignmentRepository.save(assignment);
    }

    public List<BillerAssignment> getBillersByCity(Long cityId) {
        return billerAssignmentRepository.findByCityIdAndActiveTrue(cityId);
    }
}

