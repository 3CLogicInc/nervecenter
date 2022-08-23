package com.ccclogic.portal.config.security.filters;


import com.ccclogic.portal.config.cache.BlackListedTokens;
import com.ccclogic.portal.config.security.authentication.OAuthAuthenticationToken;
import com.ccclogic.portal.util.JWTTokenUtil;
import com.ccclogic.portal.util.errorcodes.AuthenticationErrorCodes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/*
 * No SuccessHandler is required, as we don't need any specific flow or create a new flow (request)
 * on authentication success. On success, next filter in the chain will be called by using chain.doFilter(),
 * unlike web based login authentication, where we need to specify default offset after login.
 *
 * No FailureHandler is required, as we don't need to specify specific flow on failure. Just throws
 * ClientException. This is unlike web based authentication, where we need to specify a flow on failure like
 * redirect user to login offset or any other offset after authentication failure.
 *
 */
public class OAuthAccessTokenAuthenticationFilter extends OncePerRequestFilter {

    private AuthenticationManager authenticationManager;
    private String[] excludeUrls = null;
    private String[] includeUrls = null;

    public OAuthAccessTokenAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Optional<String> token = JWTTokenUtil.getTokenFromHeader(request);
        if (!token.isPresent() || StringUtils.isBlank(token.get()))
            throw new BadCredentialsException(AuthenticationErrorCodes.noToken);
        if (BlackListedTokens.check(token.get()))
            throw new BadCredentialsException(AuthenticationErrorCodes.invalidToken);

        Authentication authentication = new OAuthAuthenticationToken(token.get(), null);
        authentication = authenticationManager.authenticate(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (excludeUrls != null) {
            for (String urlRegex : includeUrls) {
                AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(urlRegex);
                if (antPathRequestMatcher.matches(request)) {
                    return false;
                }
            }
            for (String urlRegex : excludeUrls) {
                AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(urlRegex);
                if (antPathRequestMatcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public void setIncludeUrls(String[] includeUrls) {
        this.includeUrls = includeUrls;
    }
}
