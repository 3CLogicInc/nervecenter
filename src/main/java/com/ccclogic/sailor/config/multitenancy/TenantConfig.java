package com.ccclogic.sailor.config.multitenancy;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.ccclogic.sailor.repositories.tenant",
        entityManagerFactoryRef = "tenantEntityManager",
        transactionManagerRef = "tenantTransactionManager"
)
@EnableTransactionManagement
public class TenantConfig {

    @Autowired
    private org.springframework.core.env.Environment env;

    @Autowired
    JpaProperties jpaProperties;

    @Autowired
    CurrentTenantIdentifierResolver currentTenantIdentifierResolver;
    
    @Autowired
    MultiTenantDatasourceProvider multiTenantDatasourceProvider;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    
    @Bean
    public DataSource tenantDataSource() {
        return multiTenantDatasourceProvider.getMultiTenantDatasource();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean tenantEntityManager() {
        Map<String, Object> properties = new HashMap<>();
//        properties.putAll(jpaProperties.getHibernateProperties(tenantDataSource()));
        properties.put(Environment.MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
        properties.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider());
        properties.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
        properties.put(Environment.DIALECT, env.getProperty("hibernate.dialect"));
        properties.put("hibernate.id.new_generator_mappings","false");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(tenantDataSource());
        em.setPackagesToScan(new String[]{
                "com.ccclogic.sailor.entities.tenant"});
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    @Qualifier("tenantTransactionManager")
    public PlatformTransactionManager tenantTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                tenantEntityManager().getObject());
        return transactionManager;
    }

    @Bean
    public MultiTenantConnectionProvider multiTenantConnectionProvider(){
        return new TenantConnectionProviderImpl(tenantDataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate getJdbcTemplate() {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(tenantDataSource());
        return jdbcTemplate;
    }
}
