package com.epam.pf.trauma.backend.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig{
 
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(){
      LocalContainerEntityManagerFactoryBean factoryBean
       = new LocalContainerEntityManagerFactoryBean();
      factoryBean.setDataSource( restDataSource() );
      factoryBean.setPackagesToScan( new String[ ] { "org.rest" } );
 
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter(){
         {
            // JPA properties ...
         }
      };
      factoryBean.setJpaVendorAdapter( vendorAdapter );
      factoryBean.setJpaProperties( additionlProperties() );
 
      return factoryBean;
   }
 
   @Bean
   public DataSource restDataSource(){
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName( driverClassName );
      dataSource.setUrl( url );
      dataSource.setUsername( "restUser" );
      dataSource.setPassword( "restmy5ql" );
      return dataSource;
   }
 
   @Bean
   public PlatformTransactionManager transactionManager(){
      JpaTransactionManager transactionManager = new JpaTransactionManager();
      transactionManager.setEntityManagerFactory(
       entityManagerFactoryBean().getObject() );
 
      return transactionManager;
   }
 
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
      return new PersistenceExceptionTranslationPostProcessor();
   }
}