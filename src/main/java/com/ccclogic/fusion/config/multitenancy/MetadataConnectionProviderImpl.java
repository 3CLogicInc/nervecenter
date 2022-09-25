package com.ccclogic.fusion.config.multitenancy;

import com.ccclogic.fusion.config.security.TenantContext;
import com.ccclogic.fusion.util.constants.Constants;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
public class MetadataConnectionProviderImpl implements MultiTenantConnectionProvider {
    
    private DataSource dataSource;
    
    public MetadataConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }
    
    @Override
    public Connection getConnection(String s) throws SQLException {
        
        final Connection connection = getAnyConnection();
        try {
            connection.createStatement().execute("USE " + Constants.DATASOURCE.METADATA_SCHEMA);
        } catch (SQLException e) {
            throw new HibernateException(
                    "Problem setting schema to " + TenantContext.getCurrentCCSchema(),
                    e
            );
        }
        return connection;
    }
    
    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        
        try {
            connection.createStatement().execute("USE " + Constants.DATASOURCE.METADATA_SCHEMA);
        } catch (SQLException e) {
            throw new HibernateException("Problem setting schema to " + tenantIdentifier, e);
        }
        connection.close();
    }
    
    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }
    
    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return false;
    }
    
    @Override
    public <T> T unwrap(Class<T> aClass) {
        
        return null;
    }
}
