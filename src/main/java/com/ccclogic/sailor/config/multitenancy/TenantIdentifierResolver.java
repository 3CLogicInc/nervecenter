package com.ccclogic.sailor.config.multitenancy;


import com.ccclogic.sailor.config.security.TenantContext;
import com.ccclogic.sailor.util.constants.Constants;
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
