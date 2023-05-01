package com.ccclogic.nerve.repositories.webastra;

import com.ccclogic.nerve.entities.webastra.Callcenter;
import com.ccclogic.nerve.entities.webastra.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CallcenterRepository extends JpaRepository<Callcenter, Integer>, PagingAndSortingRepository<Callcenter, Integer> {

}
