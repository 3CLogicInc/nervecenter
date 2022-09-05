package com.ccclogic.sailor.repositories.tenant;

import com.ccclogic.sailor.entities.tenant.PortalMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MenuRepository extends JpaRepository<PortalMenu, Long> {
    List<PortalMenu> findAllByNameAndActiveIsTrueOrderByMenuOrder(String name);
}
