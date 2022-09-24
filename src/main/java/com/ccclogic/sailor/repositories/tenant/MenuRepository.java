package com.ccclogic.sailor.repositories.tenant;

import com.ccclogic.sailor.entities.tenant.PortalMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "menu", path = "menu-default")
public interface MenuRepository extends PagingAndSortingRepository<PortalMenu, Long>, JpaRepository<PortalMenu, Long> {
    List<PortalMenu> findAllByNameAndActiveIsTrueOrderByMenuOrder(String name);
}
