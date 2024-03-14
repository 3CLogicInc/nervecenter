package com.ccclogic.nerve.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DefaultRolePermissionDto {
    private List<RoleDto> roles;
    private List<PermissionDto> permissions;
    private List<RolePermissionDto> rolePermissions;
}

