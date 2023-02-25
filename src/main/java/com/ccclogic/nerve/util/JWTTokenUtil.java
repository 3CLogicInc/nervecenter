package com.ccclogic.nerve.util;


import com.ccclogic.nerve.config.security.ModelUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class JWTTokenUtil {

    private static String AUTHORIZATION_HEADER_KEY = "authorization";
    private static String BEARER_TOKEN_KEY = "Bearer";
    //// TODO: 1/11/18  Only Temporary, should come from properties file
    private static String JWT_SECRET = "UQIbGGWIeQ";

    private static Logger logger = LoggerFactory.getLogger(JWTTokenUtil.class);

    public static Optional<String> getTokenFromHeader(HttpServletRequest request) {
        Map<String, String> requestHeaders = getHeadersInfo(request);
        String token = requestHeaders.getOrDefault(AUTHORIZATION_HEADER_KEY, "");

        if (StringUtils.isBlank(token)) return Optional.empty();

        if (token.contains(BEARER_TOKEN_KEY)) {
            token = token.replaceAll(BEARER_TOKEN_KEY, "").trim();
        }
        return Optional.of(token);
    }

    private static Map<String, String> getHeadersInfo(HttpServletRequest request) {

        Map<String, String> map = new HashMap<String, String>();

        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }

    private static ModelUser getUserFromToken(String token, Boolean isAccessToken) throws IllegalAccessException, ExpiredJwtException {
        if (StringUtils.isBlank(token)) throw new IllegalAccessException("OAuth Token not found");
        logger.debug("OAuth Token : {}", token);
        // parse the token.
        Jws jws = Jwts.parser()
                .setSigningKey(JWT_SECRET.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token);

        Claims claims = (Claims) jws.getBody();

        if (claims.getExpiration().before(DateTime.now().toDate()))
            throw new IllegalStateException("Provided Token is expired");

        if (isAccessToken && SecurityUtil.isRefreshToken(claims))
            throw new ResourceAccessException("Invalid token provided");

        ModelUser modelUser = ModelUser.builder()
                .entityId(claims.get("entityId", Integer.class))
                .email(claims.get("email", String.class))
                .status(claims.get("status", String.class))
                .clientId(claims.get("client_id", String.class))
                .fullName(claims.get("fullName", String.class))
                .scopes(claims.get("scope", List.class))
                .customerId(claims.get("customerId", Integer.class))
                .centerId(claims.get("contactCenterId", Integer.class))
                .token(token)
                .build();


        List<GrantedAuthority> authorities = ((List<String>) claims.get("authorities", List.class)).stream()
                .map((authority) -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());

        modelUser.setAuthorities(authorities);
        return modelUser;
    }

    @SneakyThrows
    public static ModelUser getModelUserForInternalAuth(String token) {

        if (!org.apache.commons.lang.StringUtils.isBlank(token)) {
            return getUserFromOAuthToken(token);
        }

        User user = new User("admin",
                token,
                true,
                true,
                true,
                true,
                new ArrayList<>());

        ModelUser modelUser = new ModelUser(user, "superAdmin", 1);

        return modelUser;
    }

    public static ModelUser getUserFromOAuthToken(String token) throws IllegalAccessException, ExpiredJwtException {
        return getUserFromToken(token, true);
    }

    public static ModelUser getUserFromOAuthToken(HttpServletRequest tokenRequest) throws IllegalAccessException, ExpiredJwtException {
        String token = getTokenFromHeader(tokenRequest)
                .orElseThrow(() -> new IllegalArgumentException("Token Not Provided"));
        return getUserFromOAuthToken(token);
    }

    public static ModelUser getUserFromRefreshToken(String token) throws IllegalAccessException, ExpiredJwtException {
        return getUserFromToken(token, false);
    }

}
