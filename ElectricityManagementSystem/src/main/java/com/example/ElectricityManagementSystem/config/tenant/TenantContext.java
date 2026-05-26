package com.example.ElectricityManagementSystem.config.tenant;

public class TenantContext {
    private final static ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }
    public static String getCurrentTenant(){
        return CURRENT_TENANT.get();
    }
    public static void clearCurrentTenant(){
        CURRENT_TENANT.remove();
    }
}
