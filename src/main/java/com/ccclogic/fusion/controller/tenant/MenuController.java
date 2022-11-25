package com.ccclogic.fusion.controller.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyMenu;
import com.ccclogic.fusion.services.tenant.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/callcenters/{centerId}/menu")
public class MenuController {

    @Autowired
    MenuService menuService;

    @GetMapping
    public List<SynergyMenu> getSideMenu(){
        return menuService.getSideMenu();

    }

}
