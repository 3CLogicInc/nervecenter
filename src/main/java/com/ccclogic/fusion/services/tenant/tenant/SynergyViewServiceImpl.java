package com.ccclogic.fusion.services.tenant.tenant;

import com.ccclogic.fusion.dto.ViewConfig;
import com.ccclogic.fusion.entities.tenant.SynergyTab;
import com.ccclogic.fusion.entities.tenant.SynergyView;
import com.ccclogic.fusion.exceptions.ResourceNotFoundException;
import com.ccclogic.fusion.repositories.tenant.SynergyTabRepository;
import com.ccclogic.fusion.repositories.tenant.SynergyViewRepository;
import com.ccclogic.fusion.transformer.SynergyViewToViewConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynergyViewServiceImpl implements SynergyViewService {

    @Autowired
    SynergyTabRepository synergyTabRepository;

    @Autowired
    SynergyViewRepository synergyViewRepository;

    @Override
    public SynergyView getViewConfig(Integer viewId) {

//        Long userId = SecurityUtil.getLoggedInUser().getEntityId();

        SynergyView synergyView = synergyViewRepository.findById(viewId).orElseThrow(() -> new ResourceNotFoundException("View Id not found"));
        return synergyView;
    }
}
