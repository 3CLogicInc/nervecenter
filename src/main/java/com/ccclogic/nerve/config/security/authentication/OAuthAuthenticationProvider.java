package com.ccclogic.nerve.config.security.authentication;


import com.ccclogic.nerve.config.security.ModelUser;
import com.ccclogic.nerve.config.security.TenantContext;
import com.ccclogic.nerve.exceptions.ServiceException;
import com.ccclogic.nerve.util.JWTTokenUtil;
import com.ccclogic.nerve.util.constants.GenericErrorCodes;
import com.ccclogic.nerve.util.enums.UserRole;
import com.ccclogic.nerve.util.errorcodes.AuthenticationErrorCodes;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class OAuthAuthenticationProvider implements AuthenticationProvider {

//    private RoleService roleService;
//    private CallCenterService callcenterService;

//    public OAuthAuthenticationProvider(RoleService roleService, CallCenterService callcenterService) {
//        this.roleService = roleService;
//        this.callcenterService = callcenterService;
//    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OAuthAuthenticationToken authAuthenticationToken = null;
        String token = (String) authentication.getCredentials();
        List<Long> roleIdList = new ArrayList<>();
        try {
            ModelUser currentUser = JWTTokenUtil.getUserFromOAuthToken(token);
            authAuthenticationToken = new OAuthAuthenticationToken(token, currentUser, null);
            authAuthenticationToken.setAuthenticated(true);

        } catch (IllegalAccessException | MalformedJwtException | SignatureException e) {
            throw new BadCredentialsException(AuthenticationErrorCodes.invalidCode);
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException(AuthenticationErrorCodes.tokenExpired);
        } catch (AccessDeniedException e) {
            throw new AccessDeniedException(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception occurred while preparing authentication : " + e);
            throw new ServiceException("Error while preparing authentication");
        }
        return authAuthenticationToken;
    }

    private void setUserRoleFromAuthorities(ModelUser currentUser) {

        currentUser.getAuthorities().forEach(grantedAuthority -> {
            if (grantedAuthority.getAuthority().equalsIgnoreCase(UserRole.ROLE_ADMINISTRATOR.toString())) {
                currentUser.setAdmin(true);
            }
            if (grantedAuthority.getAuthority().equalsIgnoreCase(UserRole.ROLE_OWNER.toString())) {
                currentUser.setOwner(true);
            }
            if (grantedAuthority.getAuthority().equalsIgnoreCase(UserRole.ROLE_SUPERVISOR.toString())) {
                currentUser.setSupervisor(true);
            }
        });
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (OAuthAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /*
        If user is not admin/supervisor, then the callCenterId in the token must match the callcenterId in url.
        For admin and supervisor, such check is not required, as admin/supervisor have no callcenterId set in their access token.
     */
    private void validateCallCenterIdOfUser(ModelUser currentUser) {
        if (currentUser.getCenterId() != null && currentUser.getCenterId() > 0) {
            if (!TenantContext.getCurrentCcId().equals(currentUser.getCenterId())) {
                throw new AccessDeniedException(GenericErrorCodes.accessDenied);
            }
        }
    }

    /*
        Check if the api call has not specified any tenant. If no tenant is specified, Role from the access code is loaded.
        No permissions corresponding to the role is loaded. For such user only hasRole('rolename') / hasAnyRole('role1, role2')
        check should be used.
    */
//    private boolean noTenant(ModelUser currentUser) {
//        if (TenantContext.getCurrentCcId() == null) {
//            List<Role> roles = new ArrayList<>();
//            for (GrantedAuthority authority : currentUser.getAuthorities()) {
//                Role role = new Role();
//                role.setName(authority.getAuthority());
//                roles.add(role);
//            }
//            currentUser.setRoles(roles);
//            return true;
//        }
//        return false;
//    }

    /*
        This functions set internal role Names
        RolesNames as defined in UserRole
    */
//    private List<Role> setInternalRoleNames(List<Role> roles) {
//        for (Role role : roles) {
//            role.setName(UserRole.getUserRole(role.getId()).getValue());
//        }
//        return roles;
//    }

}
