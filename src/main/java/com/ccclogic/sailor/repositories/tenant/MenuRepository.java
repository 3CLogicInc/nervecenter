package com.ccclogic.sailor.repositories.tenant;

import com.ccclogic.sailor.entities.tenant.PortalMenu;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface MenuRepository extends PagingAndSortingRepository<PortalMenu, Long> {
    List<PortalMenu> findAllByNameAndActiveIsTrueOrderByMenuOrder(String name);
}
