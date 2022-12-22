package com.ccclogic.nerve.util;


import com.ccclogic.nerve.config.security.ModelUser;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    private static final Logger log = LoggerFactory.getLogger(SecurityUtil.class);

    private static final String JTI_KEY = "jti";
    private static final String ATI_KEY = "ati";

    public static ModelUser getLoggedInUser() {
        ModelUser user = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            user = (ModelUser) authentication.getPrincipal();
        }
        return user;
    }

    public static Boolean isRefreshToken(Claims claims) {
        return claims.containsKey(ATI_KEY);
    }
}
