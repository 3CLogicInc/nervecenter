package com.ccclogic.fusion.controller.tenant;

import com.ccclogic.fusion.dto.ViewConfig;
import com.ccclogic.fusion.entities.tenant.SynergyView;
import com.ccclogic.fusion.services.tenant.tenant.SynergyViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/callcenters/{centerId}/views")
public class SynergyViewController {

    @Autowired
    SynergyViewService synergyViewService;

    @GetMapping("/{viewId}")
    public SynergyView getViewConfig(@PathVariable Integer viewId){
        return synergyViewService.getViewConfig(viewId);
    }

}
