package com.ccclogic.nerve.services.webastra;

import com.ccclogic.nerve.dto.PermissionDto;
import com.ccclogic.nerve.dto.RoleDto;
import com.ccclogic.nerve.dto.RolePermissionDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> getRoles();

    List<PermissionDto> getPermissions();

    List<RolePermissionDto> getRolePermissions();
}
