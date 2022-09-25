package com.ccclogic.fusion.config.scheduled;

import com.ccclogic.fusion.config.multitenancy.MultiTenantDatasourceProvider;
import com.ccclogic.fusion.config.multitenancy.TenantIdentifierResolver;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TenantDatasourceSync {

    @Autowired
    MultiTenantDatasourceProvider datasourceProvider;

    @Autowired
    TenantIdentifierResolver tenantIdentifierResolver;

    @Scheduled(fixedDelayString = "${job.datasource.refresh.delay}")
    public void refreshDatasourceConnectionPool(){
        log.debug("Request to refresh datasource by polling: "+ DateTime.now());
        datasourceProvider.refreshDatasource();
    }
}
