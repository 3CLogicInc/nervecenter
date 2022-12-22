package com.ccclogic.nerve.repositories.webastra;

import com.ccclogic.nerve.entities.webastra.PK.RouteExceptionsPK;
import com.ccclogic.nerve.entities.webastra.RouteExceptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RouteExceptionsRepository extends JpaRepository<RouteExceptions, RouteExceptionsPK>, PagingAndSortingRepository<RouteExceptions, RouteExceptionsPK> {
}
