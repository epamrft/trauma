package com.epam.pf.trauma.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.stereotype.Component;

@Component 
@Profile("default")
public class LocalJPAConfig implements DataSourceConfiguration {

	@Bean
	public DataSource dataSource() throws Exception {
		
			EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
			return builder.setType(EmbeddedDatabaseType.H2).build();
	
	}

}
