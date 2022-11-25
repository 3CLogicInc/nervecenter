package com.ccclogic.fusion.services.tenant.tenant;


import com.ccclogic.fusion.dto.ViewConfig;
import com.ccclogic.fusion.entities.tenant.SynergyView;

public interface SynergyViewService {
    SynergyView getViewConfig(Integer viewId);
}
