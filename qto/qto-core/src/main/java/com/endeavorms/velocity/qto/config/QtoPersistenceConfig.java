package com.endeavorms.velocity.qto.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    // You said Spring Data repositories = 0, but this is harmless and future-proof.
    // If you do have repos later, point them to the right EMF/TX manager.
    basePackages = "com.endeavorms.velocity.qto",
    entityManagerFactoryRef = "qtoEntityManagerFactory",
    transactionManagerRef = "qtoTransactionManager"
)
public class QtoPersistenceConfig {

  @Bean
  @ConfigurationProperties("app.datasource.qto")
  public DataSourceProperties qtoDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Primary
  @Bean(name = "qtoDataSource")
  public DataSource qtoDataSource() {
    return qtoDataSourceProperties().initializeDataSourceBuilder().build();
  }

  @Primary
  @Bean(name = "qtoEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean qtoEntityManagerFactory(
      EntityManagerFactoryBuilder builder,
      @Qualifier("qtoDataSource") DataSource dataSource) {

    return builder
        .dataSource(dataSource)
        // Scan your domain entities. If later you want cleaner separation, we can narrow this package.
        .packages("com.endeavorms.velocity.qto")
        .persistenceUnit("qto")
        .build();
  }

  @Primary
  @Bean(name = "qtoTransactionManager")
  public PlatformTransactionManager qtoTransactionManager(
      @Qualifier("qtoEntityManagerFactory") EntityManagerFactory emf) {
    return new JpaTransactionManager(emf);
  }
}