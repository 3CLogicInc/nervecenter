package com.ccclogic.nerve.config.security.filters;


import com.ccclogic.nerve.config.cache.BlackListedTokens;
import com.ccclogic.nerve.config.security.ModelUser;
import com.ccclogic.nerve.config.security.authentication.OAuthAuthenticationToken;
import com.ccclogic.nerve.exceptions.ErrorResponse;
import com.ccclogic.nerve.util.JWTTokenUtil;
import com.ccclogic.nerve.util.JsonUtil;
import com.ccclogic.nerve.util.errorcodes.AuthenticationErrorCodes;
import com.ccclogic.nerve.util.logger.Debug;
import com.ccclogic.nerve.util.logger.Info;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
@Slf4j
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

    public void internal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            if (request.getHeader("Authorization") != null) {
                String token = request.getHeader("Authorization");
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                if (!org.springframework.util.StringUtils.hasLength(token)) {
                    throw new AccessDeniedException("invalid_token");
                }
                ModelUser currentUser = JWTTokenUtil.getUserFromOAuthToken(request);
                OAuthAuthenticationToken authentication = new OAuthAuthenticationToken(token, currentUser, authorities);
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            if (inVPCRange(request.getRemoteAddr())) {
                String token = request.getHeader("X-Internal-Authorization");
                ModelUser currentUser = JWTTokenUtil.getModelUserForInternalAuth(token);

                OAuthAuthenticationToken authentication = new OAuthAuthenticationToken(token, currentUser, new ArrayList<>());
                authentication.setAuthenticated(true);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }


            filterChain.doFilter(request, response);
        } catch (IllegalAccessException e) {
            ErrorResponse errorResponse = new ErrorResponse("invalid_code", "Access code is not valid.");
            flushErrorMessage(request, response, HttpServletResponse.SC_UNAUTHORIZED, e, errorResponse);
        } catch (ExpiredJwtException e) {
            ErrorResponse errorResponse = new ErrorResponse("token_expired", "Access code has expired.");
            flushErrorMessage(request, response, HttpServletResponse.SC_FORBIDDEN, e, errorResponse);
        } catch (MalformedJwtException e) {
            ErrorResponse errorResponse = new ErrorResponse("invalid_code", "Access code is not valid.");
            flushErrorMessage(request, response, HttpServletResponse.SC_FORBIDDEN, e, errorResponse);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse("unknown_error", "Unknown error has occured.");
            flushErrorMessage(request, response, HttpServletResponse.SC_FORBIDDEN, e, errorResponse);
        }
    }

    private boolean inVPCRange(String ip) {
        boolean isInternal = ip.startsWith("192.") ||
                ip.startsWith("172.") ||
                ip.startsWith("10") ||
                ip.equals("0:0:0:0:0:0:0:1");

        log.debug("Remote Address {} is private : {}", ip, isInternal);
        return isInternal;
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

    private void flushErrorMessage(HttpServletRequest request, HttpServletResponse response,
                                   int responseStatus, Exception ex, ErrorResponse errorResponse) {
        try {
            log.error(ex.getMessage(), ex);

            if ((request.getParameter("debug") != null && "true".equals(request.getParameter("debug")))) {
                errorResponse.setStackTrace(ExceptionUtils.getFullStackTrace(ex));
                response.getWriter().println(JsonUtil.convertObjectToStringUsingView(errorResponse, Debug.class));
            } else {
                response.getWriter().println(JsonUtil.convertObjectToStringUsingView(errorResponse, Info.class));
            }

            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            response.setStatus(responseStatus);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("Error while flushing error message", e);
        }
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public void setIncludeUrls(String[] includeUrls) {
        this.includeUrls = includeUrls;
    }
}
