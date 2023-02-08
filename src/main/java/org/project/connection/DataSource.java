package org.project.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSource {

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

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
            exception.printStackTrace();
        }

        ds = new HikariDataSource(config);

    }

    private DataSource() {}

    public static synchronized Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
