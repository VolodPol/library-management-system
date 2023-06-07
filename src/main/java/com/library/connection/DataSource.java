package com.library.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * The DataSource for hikari connection pool with static initialization of configuration
 * Classic thread-safe Singleton
 */
public class DataSource {
    private static final Logger log = LoggerFactory.getLogger(DataSource.class);
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        try (InputStream inStream = DataSource.class.getResourceAsStream("/db.properties")) {
            Properties properties = new Properties();
            properties.load(inStream);

            config.setDriverClassName(properties.getProperty("DatabaseName"));
            config.setJdbcUrl(properties.getProperty("URL"));
            config.setUsername(properties.getProperty("Username"));
            config.setPassword(properties.getProperty("Password"));

            config.addDataSourceProperty("cachePrepStmts", properties.getProperty("cachePrepStmts"));
            config.addDataSourceProperty("prepStmtCacheSize", properties.getProperty("prepStmtCacheSize"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit", properties.getProperty("prepStmtCacheSqlLimit"));

        } catch (IOException exception){
            log.error("Couldn't read DB properties");
        }
    }

    private DataSource() {}

    /**
     * The method is synchronized as may be accessed by multiple threads concurrently in a multithreaded environment
     * @return Connection to access DB
     * @throws SQLException may throw
     */
    public static synchronized Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }
    public static synchronized HikariDataSource getDataSource() {
        if (ds == null) {
            ds = new HikariDataSource(config);
        }
        return ds;
    }
}
