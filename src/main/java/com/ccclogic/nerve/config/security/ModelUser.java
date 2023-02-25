package com.ccclogic.nerve.config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelUser {

    private String userName;
    private Integer entityId;
    private Integer centerId;
    private Integer customerId;
    private String email;
    private String fullName;
    private String clientId;
    private String status;
    private List<GrantedAuthority> authorities;
    private List<String> scopes;
    private String token;
    private boolean admin;
    private boolean supervisor;
    private boolean owner;

    public ModelUser(User user, String name, Integer entityId) throws IllegalArgumentException {
        setEntityId(entityId);
        setFullName(name);
        setUserName(user.getUsername());
        setToken(user.getUsername());
        if (getAuthorities() != null) {
            for (GrantedAuthority authority : getAuthorities()) {
                if ("ROLE_OWNER".equals(authority.getAuthority()))
                    this.owner = true;
                else if ("ROLE_ADMINISTRATOR".equals(authority.getAuthority()))
                    this.admin = true;
                else if ("ROLE_SUPERVISOR".equals(authority.getAuthority()))
                    this.supervisor = true;
            }
        }
    }

}
