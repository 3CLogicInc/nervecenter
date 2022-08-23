package com.ccclogic.portal.config.multitenancy;

import java.util.HashMap;
import java.util.Map;

public class MultiTenantDomainHolder {

    private static MultiTenantDomainHolder ourInstance = new MultiTenantDomainHolder();
    private static Map<String, String> callcenterDomainMap = new HashMap<>();

    public static MultiTenantDomainHolder getInstance() {
        return ourInstance;
    }

    private MultiTenantDomainHolder() {
    }

    public String getDomain(String ccId){
        return callcenterDomainMap.getOrDefault(ccId, null);
    }

    public void setDomain(String ccId, String domain){
        callcenterDomainMap.put(ccId, domain);
    }
}
