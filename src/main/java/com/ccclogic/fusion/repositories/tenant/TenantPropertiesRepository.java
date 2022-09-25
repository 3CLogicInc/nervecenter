package com.ccclogic.fusion.repositories.tenant;

import com.ccclogic.fusion.entities.tenant.TenantProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantPropertiesRepository extends PagingAndSortingRepository<TenantProperties, Long>, JpaRepository<TenantProperties, Long> {
}
