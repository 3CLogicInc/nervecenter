package com.ccclogic.sailor.config.multitenancy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "com.ccclogic.sailor.repositories.webastra",
        entityManagerFactoryRef = "webastraEntityManagerFactory",
        transactionManagerRef = "webastraTransactionManager"
)
public class WebastraDatasourceConfig {

    @Autowired
    private Environment env;

    @Bean("webastraEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean metadataEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(webastraDataSource());
        em.setPackagesToScan("com.ccclogic.ms.entities.ls");

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
    @Primary
    public DataSource webastraDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("webastra.spring.datasource.url"));
        config.setUsername(env.getProperty("webastra.spring.datasource.username"));
        config.setPassword(env.getProperty("webastra.spring.datasource.password"));
        config.setDriverClassName(env.getProperty("webastra.spring.datasource.driver"));
        config.setConnectionTestQuery(env.getProperty("webastra.spring.datasource.connectionTestQuery"));
        config.setTransactionIsolation(env.getProperty("webastra.spring.datasource.transactionIsolation"));
        return new HikariDataSource(config);
    }


    @Bean
    @Qualifier("webastraTransactionManager")
    public PlatformTransactionManager webastraTransactionManager() {

        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                metadataEntityManagerFactory().getObject());
        return transactionManager;
    }
}
