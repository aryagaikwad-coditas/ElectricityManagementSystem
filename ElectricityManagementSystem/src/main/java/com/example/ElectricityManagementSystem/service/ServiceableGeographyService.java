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















































