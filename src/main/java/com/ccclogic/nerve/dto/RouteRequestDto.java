package com.ccclogic.nerve.dto;

import lombok.Data;

import java.util.List;

@Data
public class RouteRequestDto {
    private String name;
    private Integer domainId;
    private Boolean isDefault;

    private Integer primaryEntityId;
    private AssignUnAssignRecord assignUnAssignRecord;

    List<RouteExceptionsDto> exceptions;
}
