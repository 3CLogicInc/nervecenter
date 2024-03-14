package com.ccclogic.nerve.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RolePermissionDto {
    private Long id;
    private Integer roleId;
    private Integer permissionId;
    private Timestamp createdAt;
    private Integer createdById;
}
