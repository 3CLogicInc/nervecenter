package com.ccclogic.sailor.services.tenant;

import com.ccclogic.sailor.entities.tenant.TenantProperties;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TenantPropertiesService {
    @Transactional("tenantTransactionManager")
    List<TenantProperties> getAll();
}
