package com.ccclogic.nerve.repositories.webastra;

import com.ccclogic.nerve.entities.webastra.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface RolePermissionRepository extends JpaRepository<RolePermission, Integer>, PagingAndSortingRepository<RolePermission, Integer> {

}
