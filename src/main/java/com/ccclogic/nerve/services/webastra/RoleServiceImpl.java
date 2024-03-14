package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.dto.PermissionDto;
import com.ccclogic.nerve.dto.RoleDto;
import com.ccclogic.nerve.dto.RolePermissionDto;
import com.ccclogic.nerve.entities.webastra.Permission;
import com.ccclogic.nerve.entities.webastra.Role;
import com.ccclogic.nerve.entities.webastra.RolePermission;
import com.ccclogic.nerve.repositories.webastra.PermissionRepository;
import com.ccclogic.nerve.repositories.webastra.RolePermissionRepository;
import com.ccclogic.nerve.repositories.webastra.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public List<RoleDto> getRoles() {
        List<RoleDto> roleDtos = new ArrayList<>();
        List<Role> roles =  roleRepository.findAll();
        for (Role role : roles) {
            RoleDto roleDto = new RoleDto();
            roleDto.setId(role.getId());
            roleDto.setName(role.getName());
            roleDto.setDescription(role.getDescription());
            roleDto.setHierarchy(role.getHierarchy());
            roleDtos.add(roleDto);
        }
        return roleDtos;
    }

    @Override
    public List<PermissionDto> getPermissions() {
        List<PermissionDto> permissionDtos = new ArrayList<>();
        List<Permission> permissions = permissionRepository.findAll();
        for (Permission permission : permissions) {
            PermissionDto permissionDto = new PermissionDto();
            permissionDto.setId(permission.getId());
            permissionDto.setName(permission.getName());
            permissionDto.setPermissionGroup(permission.getPermissionGroup());
            permissionDtos.add(permissionDto);
        }
        return permissionDtos;
    }

    @Override
    public List<RolePermissionDto> getRolePermissions() {
        List<RolePermissionDto> rolePermissionDtos = new ArrayList<>();
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll();
        for (RolePermission rolePermission : rolePermissions) {
            RolePermissionDto rolePermissionDto = new RolePermissionDto();
            rolePermissionDto.setRoleId(rolePermission.getRoleId());
            rolePermissionDto.setPermissionId(rolePermission.getPermissionId());
            rolePermissionDtos.add(rolePermissionDto);
        }
        return rolePermissionDtos;
    }
}
