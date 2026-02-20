package com.endeavorms.velocity.qto.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class QtoLiquibaseConfig {

  @Bean
  public SpringLiquibase qtoLiquibase(@Qualifier("qtoDataSource") DataSource qtoDataSource) {
    SpringLiquibase liquibase = new SpringLiquibase();
    liquibase.setDataSource(qtoDataSource);

    // This matches your qto-app Liquibase changelog path
    liquibase.setChangeLog("classpath:database/changelog-master.xml");

    // This matches your mysql profile contexts
    liquibase.setContexts("common,dev");
    return liquibase;
  }
}