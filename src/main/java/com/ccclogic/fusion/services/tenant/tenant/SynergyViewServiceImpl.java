package com.ccclogic.fusion.services.tenant.tenant;

import com.ccclogic.fusion.entities.tenant.SynergyViewContainer;
import com.ccclogic.fusion.exceptions.ResourceNotFoundException;
import com.ccclogic.fusion.repositories.tenant.SynergyTabRepository;
import com.ccclogic.fusion.repositories.tenant.SynergyViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynergyViewServiceImpl implements SynergyViewService {

    @Autowired
    SynergyTabRepository synergyTabRepository;

    @Autowired
    SynergyViewRepository synergyViewRepository;

    @Override
    public SynergyViewContainer getViewConfig(Integer viewId) {

//        Long userId = SecurityUtil.getLoggedInUser().getEntityId();

        SynergyViewContainer synergyViewContainer = synergyViewRepository.findById(viewId).orElseThrow(() -> new ResourceNotFoundException("View Id not found"));
        return synergyViewContainer;
    }
}
