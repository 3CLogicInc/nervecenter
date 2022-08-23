package com.ccclogic.sailor.config.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OAuthAuthenticationToken extends AbstractAuthenticationToken{

	private static final long serialVersionUID = 2338577216937322020L;
	private final Object credential;
	private final Object principal;
	
	public OAuthAuthenticationToken(Object credential, Object principal){
		this(credential, principal, null);
	}
	
	public OAuthAuthenticationToken(Object credential, Object principal,
                                    Collection<? extends GrantedAuthority> authorities){
		super(authorities);
		this.credential = credential;
		this.principal = principal;
	}

	@Override
	public Object getCredentials() {
		return credential;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

}
