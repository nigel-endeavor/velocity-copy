package com.endeavorms.velocity.qto.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "com.endeavorms.velocity.qto",
    entityManagerFactoryRef = "platformEntityManagerFactory",
    transactionManagerRef = "platformTransactionManager"
)
public class PlatformPersistenceConfig {

  @Bean
  @ConfigurationProperties("app.datasource.platform")
  public DataSourceProperties platformDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean(name = "platformDataSource")
  public DataSource platformDataSource() {
    return platformDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Bean(name = "platformEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean platformEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("platformDataSource") DataSource dataSource) {

    return builder
        .dataSource(dataSource)
        .packages("com.endeavorms.velocity.qto")
        .persistenceUnit("platform")
        .build();
  }

  @Bean(name = "platformTransactionManager")
  public PlatformTransactionManager platformTransactionManager(
      @Qualifier("platformEntityManagerFactory") EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}
