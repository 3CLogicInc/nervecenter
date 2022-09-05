package com.ccclogic.sailor.controller.tenant;

import com.ccclogic.sailor.entities.tenant.PortalMenu;
import com.ccclogic.sailor.services.tenant.MenuService;
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
    public List<PortalMenu> getSideMenu(){
        return menuService.getSideMenu();

    }

}
