package com.ccclogic.fusion.config.multitenancy;

import com.ccclogic.fusion.config.security.TenantContext;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class CCCLogicRoutingDatasource extends AbstractRoutingDataSource{

    @Override
    protected Object determineCurrentLookupKey() {
        String schema = TenantContext.getCurrentCCSchema();
        return schema;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return super.getConnection();
    }


}
