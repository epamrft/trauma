package com.epam.pf.trauma.backend.config;

import javax.sql.DataSource;

public interface DataSourceConfiguration {
	DataSource dataSource() throws Exception;

}
