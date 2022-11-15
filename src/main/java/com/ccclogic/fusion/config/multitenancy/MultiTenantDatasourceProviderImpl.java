package com.ccclogic.fusion.config.multitenancy;


import com.ccclogic.fusion.services.webastra.CallcenterDomainService;
import com.ccclogic.fusion.util.constants.Constants;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MultiTenantDatasourceProviderImpl implements MultiTenantDatasourceProvider {

    @Autowired
    private CallcenterDomainService callcenterDomainService;

    @Autowired
    Environment env;

    CCCLogicRoutingDatasource routingDatasource = null;

    Map<Object, Object> datasources = new HashMap<>();

    @PostConstruct
    public void init() {
        routingDatasource = getRoutingDatasource();
    }

    @Override
    public DataSource getMultiTenantDatasource() {

        if (routingDatasource != null) {
            return routingDatasource;
        }

        routingDatasource = getRoutingDatasource();

        return routingDatasource;
    }

    public void refreshDatasource() {
        List<Long> callcenterDomains = callcenterDomainService.getDomainsOfCallcenters();
        log.info("About to refresh database, Recieved ccid: {}", callcenterDomains);

        Set<Long> callcenterIdsFromLS = callcenterDomains.stream().collect(Collectors.toSet());
        Set<Long> callcenterIdsFromDatasources = datasources.keySet().stream().map(datasource -> (String) datasource)
                .map(datasource -> datasource.replace(Constants.DATASOURCE.CC_SCHEMA_PREFIX, ""))
                .filter(StringUtils::isNumeric)
                .map(Long::parseLong)
                .collect(Collectors.toSet());


        Boolean ccHaveNotChanged = callcenterIdsFromLS.equals(callcenterIdsFromDatasources);

        //check if refresh is required
        if (!ccHaveNotChanged) {
            log.debug("Datasource need to be refreshed.");
            routingDatasource = getRoutingDatasource();
        }
    }

    @Override
    public boolean exists(String schema) {
        return datasources.containsKey(schema);
    }

    private CCCLogicRoutingDatasource getRoutingDatasource() {

        List<Long> ccIds = callcenterDomainService.getDomainsOfCallcenters();
        DataSource dataSource = getDatasource();
        ccIds.forEach((ccId -> datasources.put("tenant_" + ccId, dataSource)));
        datasources.putIfAbsent(Constants.DATASOURCE.DEFAULT_SCHEMA, dataSource);
        CCCLogicRoutingDatasource routingDatasource = new CCCLogicRoutingDatasource();
        routingDatasource.setTargetDataSources(datasources);
        routingDatasource.afterPropertiesSet();
        return routingDatasource;
    }

    DataSource getDatasource() {

        HikariConfig config = new HikariConfig();
        config.setPoolName("Tenant Pool : "+env.getProperty("tenant.spring.datasource.url"));
        config.setJdbcUrl(env.getProperty("tenant.spring.datasource.url"));
        config.setUsername(env.getProperty("tenant.spring.datasource.username"));
        config.setPassword(env.getProperty("tenant.spring.datasource.password"));
        config.setDriverClassName(env.getProperty("tenant.spring.datasource.driver"));
        config.setConnectionTestQuery(env.getProperty("tenant.spring.datasource.connectionTestQuery"));
        config.setTransactionIsolation(env.getProperty("tenant.spring.datasource.transactionIsolation"));

        return new HikariDataSource(config);
    }
}
