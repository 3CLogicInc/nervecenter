package com.ccclogic.fusion.services.tenant.tenant;

import com.ccclogic.fusion.dto.ViewConfig;
import com.ccclogic.fusion.entities.tenant.SynergyView;
import com.ccclogic.fusion.exceptions.ResourceNotFoundException;
import com.ccclogic.fusion.repositories.tenant.SynergyViewRepository;
import com.ccclogic.fusion.transformer.SynergyViewToViewConfig;
import com.ccclogic.fusion.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SynergyViewServiceImpl implements SynergyViewService {

    @Autowired
    SynergyViewRepository synergyViewRepository;

    @Override
    public ViewConfig getViewConfig(Integer viewId) {

//        Long userId = SecurityUtil.getLoggedInUser().getEntityId();

        SynergyView synergyView = synergyViewRepository.findById(viewId).orElseThrow(() -> new ResourceNotFoundException("View Id not found"));
        return new SynergyViewToViewConfig().transform(synergyView);
    }
}
