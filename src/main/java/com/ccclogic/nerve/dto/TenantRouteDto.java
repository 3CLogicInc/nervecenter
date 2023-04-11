package com.ccclogic.nerve.dto;

import com.ccclogic.nerve.entities.webastra.Callcenter;
import lombok.Data;

import java.util.List;

@Data
public class TenantRouteDto {
    Callcenter callcenter;
    List<IdNamePair> assignedRoutes;
}
