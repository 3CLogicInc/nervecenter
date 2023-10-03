package com.ccclogic.nerve.dto;

import com.ccclogic.nerve.entities.webastra.Callcenter;
import com.ccclogic.nerve.entities.webastra.Domain;
import com.ccclogic.nerve.entities.webastra.RouteExceptions;
import lombok.Data;

import java.util.List;

@Data
public class RouteCallcenterDto {

    private Integer id;
    private String name;
    private Integer domainId;
    private Boolean isDefault;
    private java.sql.Timestamp createdAt;;
    private java.sql.Timestamp updatedAt;
    private Domain domain;
    private List<RouteExceptions> routeExceptions;
    private List<AssignedCallcenterInterface> assignedCallcenters;
}
