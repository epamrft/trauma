package com.epam.pf.trauma.backend.config;

import java.util.Collection;

import javax.sql.DataSource;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.cloudfoundry.runtime.env.RdbmsServiceInfo;
import org.cloudfoundry.runtime.service.relational.RdbmsServiceCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component 
@Profile("cloud")
public class CloudJPAConfig implements DataSourceConfiguration {

    private CloudEnvironment cloudEnvironment = new CloudEnvironment();

    @Bean
    public DataSource dataSource() throws Exception {
        Collection<RdbmsServiceInfo> mysqlSvc = cloudEnvironment.getServiceInfos(RdbmsServiceInfo.class);
        RdbmsServiceCreator dataSourceCreator = new RdbmsServiceCreator();
        return dataSourceCreator.createService(mysqlSvc.iterator().next());
    }
    
}