package com.ccclogic.nerve.controller;


import com.ccclogic.nerve.dto.AssignUnAssignRecord;
import com.ccclogic.nerve.dto.IdNamePair;
import com.ccclogic.nerve.dto.TenantRouteDto;
import com.ccclogic.nerve.services.webastra.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantRouteController {

    @Autowired
    RouteService routeService;

    @GetMapping("/{tenantId}/routes/assigned")
    public List<IdNamePair> getAssignedTenantRoutes(Integer tenantId) {
        return routeService.getAssignedTenantRoutes(tenantId);
    }


    @GetMapping("/{tenantId}/routes/unassigned")
    public List<IdNamePair> getUnAssignedTenantRoutes(Integer tenantId) {
        return routeService.getUnAssignedTenantRoutes(tenantId);
    }

    @PutMapping("/{tenantId}/routes/assign")
    public void assignRoutesToTenant(@PathVariable Integer tenantId, @RequestBody AssignUnAssignRecord assignRecord) {
        routeService.assignRoutesToTenant(tenantId, assignRecord);
    }


    @PutMapping("routes")
    public List<TenantRouteDto> getTenantRoutes() {
        return routeService.getTenantRoutes();
    }

}
