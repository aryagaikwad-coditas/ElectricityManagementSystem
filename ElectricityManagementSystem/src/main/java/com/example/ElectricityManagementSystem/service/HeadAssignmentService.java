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











































/*
@Service
@RequiredArgsConstructor
public class HeadAssignmentService {

    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private final DistrictRepository districtRepository;
    private final CityRepository cityRepository;
    private final AreaRepository areaRepository;
    private final StateHeadAssignmentRepository stateHeadAssignmentRepository;
    private final DistrictHeadAssignmentRepository districtHeadAssignmentRepository;
    private final CityHeadAssignmentRepository cityHeadAssignmentRepository;
    private final TechnicianAreaAssignmentRepository technicianAreaAssignmentRepository;
    private final BillerAssignmentRepository billerAssignmentRepository;

    // ── STATE HEAD ──────────────────────────────────────

    // Called by MANAGEMENT
    // One person can be STATE_HEAD of multiple states
    @Transactional
    public void assignStateHead(AssignStateHeadRequest req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate user has STATE_HEAD role
        boolean hasRole = user.getRoles().stream()
                .anyMatch(r -> r.getName() == RoleType.STATE_HEAD);
        if (!hasRole) {
            throw new RuntimeException("User does not have STATE_HEAD role");
        }

        State state = stateRepository.findById(req.getStateId())
                .orElseThrow(() -> new RuntimeException("State not found"));

        // Check if assignment already exists
        boolean exists = stateHeadAssignmentRepository
                .findByStateIdAndActiveTrue(req.getStateId())
                .stream()
                .anyMatch(a -> a.getUser().getId().equals(req.getUserId()));

        if (exists) {
            throw new RuntimeException(
                "This user is already STATE_HEAD of this state");
        }

        StateHeadAssignment assignment = StateHeadAssignment.builder()
                .user(user)
                .state(state)
                .build();

        stateHeadAssignmentRepository.save(assignment);
    }

    public List<StateHeadAssignment> getStateHeadsByState(Long stateId) {
        return stateHeadAssignmentRepository.findByStateIdAndActiveTrue(stateId);
    }

    public List<StateHeadAssignment> getStatesByStateHead(Long userId) {
        return stateHeadAssignmentRepository.findByUserIdAndActiveTrue(userId);
    }

    @Transactional
    public void removeStateHead(Long userId, Long stateId) {
        stateHeadAssignmentRepository
                .findByStateIdAndActiveTrue(stateId)
                .stream()
                .filter(a -> a.getUser().getId().equals(userId))
                .findFirst()
                .ifPresent(a -> {
                    a.setActive(false);
                    stateHeadAssignmentRepository.save(a);
                });
    }

    // ── DISTRICT HEAD ───────────────────────────────────

    // Called by STATE_HEAD
    // One person can be DISTRICT_HEAD of multiple districts
    // A STATE_HEAD can also be a DISTRICT_HEAD (same person)
    @Transactional
    public void assignDistrictHead(AssignDistrictHeadRequest req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean hasRole = user.getRoles().stream()
                .anyMatch(r -> r.getName() == RoleType.DISTRICT_HEAD
                            || r.getName() == RoleType.STATE_HEAD);
        if (!hasRole) {
            throw new RuntimeException(
                "User must have DISTRICT_HEAD or STATE_HEAD role");
        }

        District district = districtRepository.findById(req.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District not found"));

        boolean exists = districtHeadAssignmentRepository
                .findByDistrictIdAndActiveTrue(req.getDistrictId())
                .stream()
                .anyMatch(a -> a.getUser().getId().equals(req.getUserId()));

        if (exists) {
            throw new RuntimeException(
                "This user is already DISTRICT_HEAD of this district");
        }

        DistrictHeadAssignment assignment = DistrictHeadAssignment.builder()
                .user(user)
                .district(district)
                .build();

        districtHeadAssignmentRepository.save(assignment);
    }

    public List<DistrictHeadAssignment> getDistrictHeadsByDistrict(Long districtId) {
        return districtHeadAssignmentRepository
                .findByDistrictIdAndActiveTrue(districtId);
    }

    public List<DistrictHeadAssignment> getDistrictsByDistrictHead(Long userId) {
        return districtHeadAssignmentRepository.findByUserIdAndActiveTrue(userId);
    }

    @Transactional
    public void removeDistrictHead(Long userId, Long districtId) {
        districtHeadAssignmentRepository
                .findByDistrictIdAndActiveTrue(districtId)
                .stream()
                .filter(a -> a.getUser().getId().equals(userId))
                .findFirst()
                .ifPresent(a -> {
                    a.setActive(false);
                    districtHeadAssignmentRepository.save(a);
                });
    }

    // ── CITY HEAD ───────────────────────────────────────

    // Called by DISTRICT_HEAD
    @Transactional
    public void assignCityHead(AssignCityHeadRequest req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean hasRole = user.getRoles().stream()
                .anyMatch(r -> r.getName() == RoleType.CITY_HEAD
                            || r.getName() == RoleType.DISTRICT_HEAD);
        if (!hasRole) {
            throw new RuntimeException(
                "User must have CITY_HEAD or DISTRICT_HEAD role");
        }

        City city = cityRepository.findById(req.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));

        boolean exists = cityHeadAssignmentRepository
                .findByCityIdAndActiveTrue(req.getCityId())
                .stream()
                .anyMatch(a -> a.getUser().getId().equals(req.getUserId()));

        if (exists) {
            throw new RuntimeException(
                "This user is already CITY_HEAD of this city");
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

    // ── TECHNICIAN AREA ASSIGNMENT ──────────────────────

    // Called by CITY_HEAD
    @Transactional
    public void assignTechnicianToArea(AssignTechnicianToAreaRequest req) {

        User technician = userRepository.findById(req.getTechnicianId())
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        boolean hasRole = technician.getRoles().stream()
                .anyMatch(r -> r.getName() == RoleType.TECHNICIAN);
        if (!hasRole) {
            throw new RuntimeException("User does not have TECHNICIAN role");
        }

        Area area = areaRepository.findById(req.getAreaId())
                .orElseThrow(() -> new RuntimeException("Area not found"));

        boolean exists = technicianAreaAssignmentRepository
                .findByAreaIdAndActiveTrue(req.getAreaId())
                .stream()
                .anyMatch(a -> a.getTechnician().getId()
                        .equals(req.getTechnicianId()));

        if (exists) {
            throw new RuntimeException(
                "Technician already assigned to this area");
        }

        TechnicianAreaAssignment assignment = TechnicianAreaAssignment.builder()
                .technician(technician)
                .area(area)
                .build();

        technicianAreaAssignmentRepository.save(assignment);
    }

    public List<TechnicianAreaAssignment> getTechniciansByArea(Long areaId) {
        return technicianAreaAssignmentRepository.findByAreaIdAndActiveTrue(areaId);
    }

    // ── BILLER CITY ASSIGNMENT ──────────────────────────

    // Called by CITY_HEAD
    @Transactional
    public void assignBillerToCity(AssignBillerToCityRequest req) {

        User biller = userRepository.findById(req.getBillerId())
                .orElseThrow(() -> new RuntimeException("Biller not found"));

        boolean hasRole = biller.getRoles().stream()
                .anyMatch(r -> r.getName() == RoleType.BILLER);
        if (!hasRole) {
            throw new RuntimeException("User does not have BILLER role");
        }

        City city = cityRepository.findById(req.getCityId())
                .orElseThrow(() -> new RuntimeException("City not found"));

        boolean exists = billerAssignmentRepository
                .findByCityIdAndActiveTrue(req.getCityId())
                .stream()
                .anyMatch(a -> a.getBiller().getId().equals(req.getBillerId()));

        if (exists) {
            throw new RuntimeException(
                "Biller already assigned to this city");
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
 */