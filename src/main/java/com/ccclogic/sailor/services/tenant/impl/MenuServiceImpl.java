package com.ccclogic.sailor.services.tenant.impl;

import com.ccclogic.sailor.entities.tenant.PortalMenu;
import com.ccclogic.sailor.repositories.tenant.MenuRepository;
import com.ccclogic.sailor.services.tenant.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Override
    public List<PortalMenu> getSideMenu() {
        return menuRepository.findAllByNameAndActiveIsTrueOrderByMenuOrder("portal");
    }
}
