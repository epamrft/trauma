package com.epam.pf.trauma.backend.config;

import javax.sql.DataSource;

import org.cloudfoundry.runtime.env.CloudEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

@Component 
@Profile("default")
public class CloudJPAConfig implements DataSourceConfiguration {

    private CloudEnvironment cloudEnvironment = new CloudEnvironment();

    @Bean
    public DataSource dataSource() throws Exception {
    	String driverClassName="com.mysql.jdbc.Driver";
    	String url = "jdbc:mysql://localhost:10000/";
    	String username="udIxVY8wfbU9w";
    	String password= "pT5axhz1qq0NY";
        @SuppressWarnings("deprecation")
		DataSource dataSource = new DriverManagerDataSource(driverClassName, url, username, password);
    
        
        return dataSource;
    }
    
}