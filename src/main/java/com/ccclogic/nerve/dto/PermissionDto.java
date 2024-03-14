package com.ccclogic.nerve.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PermissionDto {
    private Integer id;
    private String permissionGroup;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Integer createdById;
    private Integer updatedById;
}

