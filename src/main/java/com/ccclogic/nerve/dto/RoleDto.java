package com.ccclogic.nerve.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RoleDto {
    private Integer id;
    private String name;
    private String description;
    private Integer hierarchy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer createdById;
    private Integer updatedById;
}
