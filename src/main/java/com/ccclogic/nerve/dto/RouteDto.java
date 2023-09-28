package com.ccclogic.nerve.dto;

import com.ccclogic.nerve.entities.webastra.Callcenter;
import com.ccclogic.nerve.entities.webastra.RouteExceptions;
import com.ccclogic.nerve.entities.webastra.TenantRoute;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteDto {
    private Integer id;
    private String name;
    private Integer domainId;
    private Boolean isDefault;
    private java.sql.Timestamp createdAt;;
    private java.sql.Timestamp updatedAt;
    private List<RouteExceptions> routeExceptions;
    private Set<Callcenter> assignedCallcenters;
}
