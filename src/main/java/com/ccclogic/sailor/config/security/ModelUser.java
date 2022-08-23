package com.ccclogic.sailor.config.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelUser {

    private String userName;
    private Long entityId;
    private Long centerId;
    private Long customerId;
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

}
