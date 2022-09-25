package com.ccclogic.fusion.controller;

import com.ccclogic.fusion.entities.tenant.TenantProperties;
import com.ccclogic.fusion.services.tenant.TenantPropertiesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/callcenters/{centerId}/properties")
public class PropertiesController {

    @Autowired
    TenantPropertiesServiceImpl tenantPropertiesService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TenantProperties> getProperties(){
        return tenantPropertiesService.getAll();
    }
}
