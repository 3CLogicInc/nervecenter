package com.ccclogic.nerve.repositories.webastra;

import com.ccclogic.nerve.dto.AssignedCallcenterDto;
import com.ccclogic.nerve.dto.AssignedCallcenterInterface;
import com.ccclogic.nerve.dto.IdNamePair;
import com.ccclogic.nerve.entities.webastra.Callcenter;
import com.ccclogic.nerve.entities.webastra.TenantRoute;
import com.ccclogic.nerve.entities.webastra.PK.CallcenterRoutePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface TenantRouteRepository extends JpaRepository<TenantRoute, CallcenterRoutePK>, PagingAndSortingRepository<TenantRoute, CallcenterRoutePK> {

    @Query("SELECT new com.ccclogic.nerve.dto.IdNamePair(r.id, r.name) FROM Route r WHERE r.id IN (SELECT rt.routeId FROM TenantRoute rt WHERE rt.callcenterId = ?1)")
    List<IdNamePair> getAssignedTenantRoutes(Integer tenantId);

    @Query("SELECT new com.ccclogic.nerve.dto.IdNamePair(r.id, r.name) FROM Route r WHERE r.id NOT IN (SELECT rt.routeId FROM TenantRoute rt WHERE rt.callcenterId = ?1)")
    List<IdNamePair> getUnAssignedTenantRoutes(Integer tenantId);

    @Modifying
    @Query("DELETE FROM TenantRoute rt WHERE rt.callcenterId = ?1 AND rt.routeId IN ?2")
    void unAssignRoutesFromTenant(Integer tenantId, List<Integer> unassign);


    @Query(value = "Select cc.id, cc.name, cc.owner, cc.is_demo, cc.status, cc.`release` from callcenters cc left join nc_callcenter_routes ncr ON cc.id = ncr.callcenter_id where ncr.route_id= :routeId", nativeQuery = true)
    List<AssignedCallcenterInterface> findAllCallcenterByRouteId(Integer routeId);
}
