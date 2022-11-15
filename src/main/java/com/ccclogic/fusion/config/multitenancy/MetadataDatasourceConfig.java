package com.ccclogic.fusion.config.multitenancy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories(
        basePackages = "com.ccclogic.fusion.repositories.metadata",
        entityManagerFactoryRef = "metadataEntityManagerFactory",
        transactionManagerRef = "metadataTransactionManager"
)
public class MetadataDatasourceConfig {

    @Autowired
    private Environment env;

    @Bean("metadataEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean metadataEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(metadataDataSource());
        em.setPackagesToScan("com.ccclogic.fusion.entities.metadata");

        HibernateJpaVendorAdapter vendorAdapter
                = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",
                env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect",
                env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);

        return em;
    }


    @Bean
    public DataSource metadataDataSource() {
        HikariConfig config = new HikariConfig();
        config.setPoolName("Tenant Metadata Pool : "+env.getProperty("tenant.spring.datasource.url"));
        config.setJdbcUrl(env.getProperty("tenant.spring.datasource.url"));
        config.setUsername(env.getProperty("tenant.spring.datasource.username"));
        config.setPassword(env.getProperty("tenant.spring.datasource.password"));
        config.setDriverClassName(env.getProperty("tenant.spring.datasource.driver"));
        config.setConnectionTestQuery(env.getProperty("tenant.spring.datasource.connectionTestQuery"));
        config.setTransactionIsolation(env.getProperty("tenant.spring.datasource.transactionIsolation"));
        return new HikariDataSource(config);
    }


    @Bean
    @Qualifier("metadataTransactionManager")
    public PlatformTransactionManager metadataTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                metadataEntityManagerFactory().getObject());
        return transactionManager;
    }
}
