package com.ccclogic.fusion.repositories.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "menu", path = "menu-default")
public interface MenuRepository extends PagingAndSortingRepository<SynergyMenu, Long>, JpaRepository<SynergyMenu, Long> {
    List<SynergyMenu> findAllByNameAndActiveIsTrueOrderByMenuOrder(String name);
}
