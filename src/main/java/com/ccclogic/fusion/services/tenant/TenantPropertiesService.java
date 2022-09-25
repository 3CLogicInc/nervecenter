package com.ccclogic.fusion.services.tenant;

import com.ccclogic.fusion.entities.tenant.TenantProperties;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TenantPropertiesService {
    @Transactional("tenantTransactionManager")
    List<TenantProperties> getAll();
}
