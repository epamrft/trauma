package com.epam.pf.trauma.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig {

	@Autowired
	private DataSourceConfiguration dataSourceConfiguration;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory()
			throws Exception {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSourceConfiguration.dataSource());
		emf.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		emf.setPersistenceUnitName("trauma-PersistenceUnit");
		emf.getJpaPropertyMap().put("hibernate.hbm2ddl.auto", "create");
		// emf.setPackagesToScan(new String[] {
		// "com.epam.pf.trauma.backend.service.domain" });

		return emf;
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(this.entityManagerFactory()
				.getObject());

		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}