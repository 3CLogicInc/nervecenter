package com.ccclogic.nerve.dto;

import lombok.Data;

import java.util.List;

@Data
public class RouteResponseDto {
    Integer id;
    private String name;
    private String domain;
    private Boolean isDefault;

    List<RouteExceptionsDto> expressions;
}
