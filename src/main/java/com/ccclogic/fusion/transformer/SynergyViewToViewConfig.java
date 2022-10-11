package com.ccclogic.fusion.transformer;

import com.ccclogic.fusion.dto.ViewConfig;
import com.ccclogic.fusion.entities.tenant.SynergyView;
import net.sf.json.JSONObject;

public class SynergyViewToViewConfig {

    public ViewConfig transform(SynergyView synergyView){

        JSONObject header = JSONObject.fromObject(synergyView.getHeader());
        JSONObject body = JSONObject.fromObject(synergyView.getBody());

        return new ViewConfig(header, body);
    }

}
