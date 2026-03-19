package com.endeavorms.velocity.qto;

import java.sql.Connection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;


/**
 * Runs Liquibase changesets. Spring Boot version - uses @Autowired for DataSource.
 */
@Component("LiquibaseStartupBean")
@ConditionalOnProperty(name = "spring.liquibase.enabled", havingValue = "true", matchIfMissing = true)
public class LiquibaseRunner {

    /** Logging Facade.*/
    protected static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseRunner.class);

    /** The relative path from the src/main/resources directory of the master changelog file.*/
    protected String masterChangeLogFile = "database/postgresql-baseline.xml";

    /** QTO DDL Datasource. In Spring Boot, configure datasource and inject by name if needed. */
    @Autowired(required = false)
    @Qualifier("dataSource")
    private DataSource dataSource;


    @PostConstruct
    protected void postConstruct() throws Exception {
        if (dataSource == null) {
            LOGGER.warn("No DataSource configured - skipping Liquibase");
            return;
        }
        String contexts = System.getProperty("liquibase.contexts", "");
        LOGGER.debug("Constructing {} with contexts: {}", this.getClass().getSimpleName(), contexts);

        ResourceAccessor accessor = new ClassLoaderResourceAccessor(Thread.currentThread().getContextClassLoader());
        Connection connection = getDataSource().getConnection();
        JdbcConnection jdbcConnection = new JdbcConnection(connection);

        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
        Liquibase liquibase = new Liquibase(getMasterChangeLogFile(), accessor, database);

        liquibase.update(contexts);

        if (connection != null) {
            connection.close();
        }
    }


    @PreDestroy
    protected void preDestroy() {
        LOGGER.debug("Destroying {}", this.getClass().getSimpleName());
    }


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
    public String getMasterChangeLogFile() {
        return masterChangeLogFile;
    }

    public void setMasterChangeLogFile(final String masterChangeLogFile) {
        this.masterChangeLogFile = masterChangeLogFile;
    }
}
