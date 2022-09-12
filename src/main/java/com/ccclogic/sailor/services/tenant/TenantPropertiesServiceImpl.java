package com.ccclogic.sailor.services.tenant;

import com.ccclogic.sailor.entities.tenant.TenantProperties;
import com.ccclogic.sailor.repositories.tenant.TenantPropertiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TenantPropertiesServiceImpl implements TenantPropertiesService{

    @Autowired
    TenantPropertiesRepository tenantPropertiesRepository;

    @Override
    public List<TenantProperties> getAll(){
        List<TenantProperties> tenantProperties = new ArrayList<>();
        tenantPropertiesRepository.findAll().forEach((t) -> tenantProperties.add(t));
        return tenantProperties;
    }
}
