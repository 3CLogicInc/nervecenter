package com.ccclogic.sailor.repositories.tenant;

import com.ccclogic.sailor.entities.tenant.TenantProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantPropertiesRepository extends JpaRepository<TenantProperties, Long> {
}
