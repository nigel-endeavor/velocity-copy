package com.vertek.corporate.qto;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import java.sql.Connection;


/**
 * Runs Liquibase changesets.
 * @author rcasey
 * @since 1.0.0
 */
@Startup
@Singleton(name = "LiquibaseStartupBean")
@TransactionManagement(TransactionManagementType.BEAN)
public class LiquibaseRunner {

    /** Logging Facade.*/
    protected static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseRunner.class);

    /** The relative path from the src/main/resources directory of the master changelog file.*/
    protected String masterChangeLogFile = "database/changelog-master.xml";

    /** QTO DDL Datasource. */
    @Resource(lookup = "java:jboss/datasources/qto-ddl")
    private DataSource dataSource;


    /**
     * Runs Liquibase updates upon Construction of the Bean.
     * @throws Exception should the Liquibase update fail.
     */
    @PostConstruct
    protected void postConstruct() throws Exception {
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


    /**
     * Runs before the Bean is destroyed.
     */
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
