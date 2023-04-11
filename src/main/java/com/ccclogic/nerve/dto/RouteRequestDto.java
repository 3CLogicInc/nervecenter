package com.ccclogic.nerve.dto;

import lombok.Data;

import java.util.List;

@Data
public class RouteRequestDto {
    private String name;
    private String domain;
    private Boolean isDefault;

    List<RouteExceptionsDto> exceptions;
}
