package com.ccclogic.fusion.services.tenant.impl;

import com.ccclogic.fusion.entities.tenant.SynergyMenu;
import com.ccclogic.fusion.repositories.tenant.MenuRepository;
import com.ccclogic.fusion.services.tenant.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    MenuRepository menuRepository;

    @Override
    public List<SynergyMenu> getSideMenu() {
        return menuRepository.findAllByNameAndActiveIsTrueOrderByMenuOrder("portal");
    }
}
