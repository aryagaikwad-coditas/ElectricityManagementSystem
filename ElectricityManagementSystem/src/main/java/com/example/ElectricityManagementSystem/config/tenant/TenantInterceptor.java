package com.example.ElectricityManagementSystem.config.tenant;

import com.example.ElectricityManagementSystem.repository.ClientRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TenantInterceptor implements HandlerInterceptor {
    @Autowired
    private ClientRepository clientRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tenantHeader = request.getHeader("X-Tenant-ID");
        if(tenantHeader == null || tenantHeader.isEmpty()){
            TenantContext.setCurrentTenant(TenantContext.getCurrentTenant());
            return true;
        }

        TenantContext.setCurrentTenant(tenantHeader);
        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) throws Exception {
        TenantContext.clearCurrentTenant();
    }
}
