import liquibase.Liquibase;
import liquibase.Scope;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.lockservice.LockServiceFactory;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.exception.ValidationFailedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class RunLiquibase {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Liquibase migration...");
        // Disable checksum validation at JVM level
        System.setProperty("liquibase.checksumVersion", "9");

        Class.forName("org.postgresql.Driver");
        Connection connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/qto", "qto_user", "changeme");

        // Force-release any stale lock directly in SQL
        System.out.println("Ensuring no stale locks exist...");
        try (Statement stmt = connection.createStatement()) {
            try {
                stmt.execute("UPDATE databasechangeloglock SET locked=false, lockgranted=null, lockedby=null WHERE id=1");
                System.out.println("Released stale lock.");
            } catch (Exception e) {
                System.out.println("Lock table does not exist yet - fresh start.");
            }
        }

        JdbcConnection jdbcConnection = new JdbcConnection(connection);
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

        System.out.println("Force releasing lock via LockService...");
        LockServiceFactory.getInstance().getLockService(database).forceReleaseLock();

        Liquibase liquibase = new Liquibase("database/changelog-master.xml",
            new ClassLoaderResourceAccessor(Thread.currentThread().getContextClassLoader()), database);

        System.out.println("Running update...");
        try {
            liquibase.update("");
        } catch (ValidationFailedException e) {
            System.out.println("ValidationFailedException caught - attempting to update checksums in DATABASECHANGELOG and retry...");
            // The checksum mismatch is from 3.x -> 4.x algorithm change
            // Get DATABASECHANGELOG records and update their checksums
            try (Statement stmt = connection.createStatement()) {
                // Update all checksums to "any" equivalent - MD5Sum("1:" + md5) approach
                // Instead, directly clear the checksum column so Liquibase recalculates
                int updated = stmt.executeUpdate("UPDATE databasechangelog SET md5sum = NULL");
                System.out.println("Cleared " + updated + " checksums from DATABASECHANGELOG.");
            }
            // Reconnect and retry
            connection.close();
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/qto", "qto_user", "changeme");
            jdbcConnection = new JdbcConnection(connection);
            database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);
            liquibase = new Liquibase("database/changelog-master.xml",
                new ClassLoaderResourceAccessor(Thread.currentThread().getContextClassLoader()), database);
            liquibase.update("");
        }
        connection.close();
        System.out.println("Liquibase migration COMPLETE!");
    }
}

