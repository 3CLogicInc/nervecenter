package com.ccclogic.nerve.repositories.webastra;

import com.ccclogic.nerve.entities.webastra.CallcenterRoute;
import com.ccclogic.nerve.entities.webastra.PK.CallcenterRoutePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CallcenterRouteRepository extends JpaRepository<CallcenterRoute, CallcenterRoutePK>, PagingAndSortingRepository<CallcenterRoute, CallcenterRoutePK> {
}
