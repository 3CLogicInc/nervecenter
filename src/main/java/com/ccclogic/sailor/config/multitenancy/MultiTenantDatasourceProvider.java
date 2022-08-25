package com.ccclogic.sailor.config.multitenancy;

import javax.sql.DataSource;

public interface MultiTenantDatasourceProvider {
    
    DataSource getMultiTenantDatasource();
    
    boolean exists(String schema);

    void refreshDatasource();
}
