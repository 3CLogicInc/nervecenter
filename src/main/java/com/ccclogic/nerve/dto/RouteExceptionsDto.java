package com.ccclogic.nerve.dto;

import lombok.Data;

@Data
public class RouteExceptionsDto {
    Integer id;
    String country;
    String prefix;
    Integer exceptionDomainId;
}
