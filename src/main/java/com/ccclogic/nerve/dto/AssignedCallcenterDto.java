package com.ccclogic.nerve.dto;

import lombok.Data;

@Data
public class AssignedCallcenterDto {
    Integer id;
    String name;
    Integer owner;
    Boolean demo;
    Integer status;
    String release;
}
