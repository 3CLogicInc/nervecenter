package com.ccclogic.nerve.controller;

import com.ccclogic.nerve.dto.DefaultRolePermissionDto;
import com.ccclogic.nerve.dto.PermissionDto;
import com.ccclogic.nerve.dto.RoleDto;
import com.ccclogic.nerve.dto.RolePermissionDto;
import com.ccclogic.nerve.entities.webastra.Permission;
import com.ccclogic.nerve.entities.webastra.Role;
import com.ccclogic.nerve.entities.webastra.RolePermission;
import com.ccclogic.nerve.services.webastra.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public DefaultRolePermissionDto getDefaultRoles() {
        List<RoleDto> roles = roleService.getRoles();
        List<PermissionDto> permissions = roleService.getPermissions();
        List<RolePermissionDto> rolePermissions = roleService.getRolePermissions();

        return new DefaultRolePermissionDto(roles, permissions, rolePermissions);
    }
}
