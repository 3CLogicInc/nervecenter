package com.ccclogic.portal.config.cache;

import java.util.HashSet;
import java.util.Set;

public class BlackListedTokens {
    
    public static Set<String> tokens = new HashSet<>();
    
    public static void add(String blacklistedToken){
        tokens.add(blacklistedToken);
    }
    
    public static boolean check(String token){
        if(token == null) return false;
        return tokens.contains(token);
    }
}
