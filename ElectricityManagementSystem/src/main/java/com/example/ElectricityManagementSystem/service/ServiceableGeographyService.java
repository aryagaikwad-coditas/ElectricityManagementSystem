package com.example.ElectricityManagementSystem.service;

import com.example.ElectricityManagementSystem.config.tenant.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceableGeographyService{

    private final JdbcTemplate jdbcTemplate;


    public void addServiceableStates(Long referenceId) {
        String s = TenantContext.getCurrentTenant();
        jdbcTemplate.update(
                "INSERT INTO " + s + ".serviceable_states (state_id, is_active) " +
                        "VALUES (?, true) " +
                        "ON CONFLICT (state_id) DO UPDATE SET is_active = true"
        );
    }

    public void removeServiceableStates(Long stateId) {
        String s = TenantContext.getCurrentTenant();
        jdbcTemplate.update("UPDATE" + s + "serviceable_state"
        + " SET is_active = false " + "WHERE state_id = ?",stateId);
    }

    public void addServiceableDistrict(Long referenceId) {
        String s = TenantContext.getCurrentTenant();
        jdbcTemplate.update("INSERT INTO " + s + "serviceable_district(district_id , is_active)"
                + "VALUES(?,true)" + "ON CONFLICT (district_id) DO UPDATE SET is_active = true");
    }

    public void removeServiceableDistrict(Long districtId) {
        String s = TenantContext.getCurrentTenant();
        jdbcTemplate.update("UPDATE" + s + "serviceable_district" + "SET is_active = false " + "WHERE district_id = ?",districtId);
    }

    public void addServiceableCity(Long referenceId) {
        String s = TenantContext.getCurrentTenant();
        jdbcTemplate.update("INSERT INTO " + s + "serviceable_city(city_id , is_active)" + "VALUES(?,true)" + "ON CONFLICT (city_id)",referenceId);
    }

    public void removeServiceableCity(Long cityId) {
        String s = TenantContext.getCurrentTenant();
        jdbcTemplate.update("UPDATE " + s + "serviceable_city" + "SET is_active = false " + "WHERE city_id = ?",cityId);
    }

    public void addServiceableArea(Long referenceId) {
        String s = TenantContext.getCurrentTenant();
        jdbcTemplate.update("INSERT " + s + "serviceable_area(area_id , is_active)" + "VALUES(?,true)" + "ON CONFLICT (area_id)",referenceId);

    }

    public void removeServiceableArea(Long areaId) {
        String s = TenantContext.getCurrentTenant();
        jdbcTemplate.update("UPDATE "+ s + "serviceable_area" + "SET is_active = false " + "WHERE area_id = ?",areaId);
    }
}






















































/*
@Service
@RequiredArgsConstructor
public class ServiceableGeographyService {

    private final JdbcTemplate jdbcTemplate;

    // ── STATES ──────────────────────────────────────────
    // OPS_HEAD marks which states this provider services

    @Transactional
    public void addServiceableState(Long stateId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "INSERT INTO " + s + ".serviceable_states (state_id, is_active) " +
            "VALUES (?, true) " +
            "ON CONFLICT (state_id) DO UPDATE SET is_active = true",
            stateId
        );
    }

    @Transactional
    public void removeServiceableState(Long stateId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "UPDATE " + s + ".serviceable_states " +
            "SET is_active = false WHERE state_id = ?",
            stateId
        );
    }

    public List<Map<String, Object>> getServiceableStates() {
        String s = TenantContext.getTenant();
        // Join with master.states to get state names
        return jdbcTemplate.queryForList(
            "SELECT ss.id, ss.state_id, ss.is_active, " +
            "ms.name as state_name, ms.code as state_code " +
            "FROM " + s + ".serviceable_states ss " +
            "JOIN master.states ms ON ss.state_id = ms.id " +
            "WHERE ss.is_active = true " +
            "ORDER BY ms.name"
        );
    }

    // ── DISTRICTS ────────────────────────────────────────
    // STATE_HEAD marks which districts they service

    @Transactional
    public void addServiceableDistrict(Long districtId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "INSERT INTO " + s + ".serviceable_districts (district_id, is_active) " +
            "VALUES (?, true) " +
            "ON CONFLICT (district_id) DO UPDATE SET is_active = true",
            districtId
        );
    }

    @Transactional
    public void removeServiceableDistrict(Long districtId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "UPDATE " + s + ".serviceable_districts " +
            "SET is_active = false WHERE district_id = ?",
            districtId
        );
    }

    public List<Map<String, Object>> getServiceableDistricts(Long stateId) {
        String s = TenantContext.getTenant();
        return jdbcTemplate.queryForList(
            "SELECT sd.id, sd.district_id, sd.is_active, " +
            "md.name as district_name " +
            "FROM " + s + ".serviceable_districts sd " +
            "JOIN master.districts md ON sd.district_id = md.id " +
            "WHERE sd.is_active = true AND md.state_id = ? " +
            "ORDER BY md.name",
            stateId
        );
    }

    // ── CITIES ───────────────────────────────────────────
    // DISTRICT_HEAD marks which cities they service

    @Transactional
    public void addServiceableCity(Long cityId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "INSERT INTO " + s + ".serviceable_cities (city_id, is_active) " +
            "VALUES (?, true) " +
            "ON CONFLICT (city_id) DO UPDATE SET is_active = true",
            cityId
        );
    }

    @Transactional
    public void removeServiceableCity(Long cityId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "UPDATE " + s + ".serviceable_cities " +
            "SET is_active = false WHERE city_id = ?",
            cityId
        );
    }

    public List<Map<String, Object>> getServiceableCities(Long districtId) {
        String s = TenantContext.getTenant();
        return jdbcTemplate.queryForList(
            "SELECT sc.id, sc.city_id, sc.is_active, " +
            "mc.name as city_name " +
            "FROM " + s + ".serviceable_cities sc " +
            "JOIN master.cities mc ON sc.city_id = mc.id " +
            "WHERE sc.is_active = true AND mc.district_id = ? " +
            "ORDER BY mc.name",
            districtId
        );
    }

    // ── AREAS ────────────────────────────────────────────
    // DISTRICT_HEAD marks which areas they service

    @Transactional
    public void addServiceableArea(Long areaId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "INSERT INTO " + s + ".serviceable_areas (area_id, is_active) " +
            "VALUES (?, true) " +
            "ON CONFLICT (area_id) DO UPDATE SET is_active = true",
            areaId
        );
    }

    @Transactional
    public void removeServiceableArea(Long areaId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "UPDATE " + s + ".serviceable_areas " +
            "SET is_active = false WHERE area_id = ?",
            areaId
        );
    }

    public List<Map<String, Object>> getServiceableAreas(Long cityId) {
        String s = TenantContext.getTenant();
        return jdbcTemplate.queryForList(
            "SELECT sa.id, sa.area_id, sa.is_active, " +
            "ma.name as area_name, ma.pincode " +
            "FROM " + s + ".serviceable_areas sa " +
            "JOIN master.areas ma ON sa.area_id = ma.id " +
            "WHERE sa.is_active = true AND ma.city_id = ? " +
            "ORDER BY ma.name",
            cityId
        );
    }

    // ── BPO GEOGRAPHIC ASSIGNMENTS ───────────────────────
    // OPS_HEAD assigns BPO staff to states/districts/cities

    @Transactional
    public void assignBpoToGeography(Long tenantUserId, String role,
                                      Long stateId, Long districtId,
                                      Long cityId) {
        String s = TenantContext.getTenant();
        jdbcTemplate.update(
            "INSERT INTO " + s + ".bpo_geographic_assignments " +
            "(tenant_user_id, role, state_id, district_id, city_id) " +
            "VALUES (?, ?, ?, ?, ?)",
            tenantUserId, role, stateId, districtId, cityId
        );
    }

    public List<Map<String, Object>> getBpoAssignments(Long tenantUserId) {
        String s = TenantContext.getTenant();
        return jdbcTemplate.queryForList(
            "SELECT bga.*, " +
            "ms.name as state_name, " +
            "md.name as district_name, " +
            "mc.name as city_name " +
            "FROM " + s + ".bpo_geographic_assignments bga " +
            "LEFT JOIN master.states ms ON bga.state_id = ms.id " +
            "LEFT JOIN master.districts md ON bga.district_id = md.id " +
            "LEFT JOIN master.cities mc ON bga.city_id = mc.id " +
            "WHERE bga.tenant_user_id = ?",
            tenantUserId
        );
    }
}
 */
