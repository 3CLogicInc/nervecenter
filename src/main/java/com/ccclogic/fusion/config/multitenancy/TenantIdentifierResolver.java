package com.ccclogic.fusion.config.multitenancy;


import com.ccclogic.fusion.config.security.TenantContext;
import com.ccclogic.fusion.util.constants.Constants;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentCCSchema();
        if (tenantId != null) {
            return tenantId;
        }
        return Constants.DATASOURCE.DEFAULT_SCHEMA;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
