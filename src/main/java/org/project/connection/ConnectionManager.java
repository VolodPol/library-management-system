package org.project.connection;

import org.project.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;

/**
 * Class to delegate operations with connection and statements with exception handling and logging
 */
public class ConnectionManager {
    private static final Logger log = LoggerFactory.getLogger(ConnectionManager.class);
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DataSource.getConnection();
        } catch (SQLException exception) {
            log.error("Failed to get connection ", exception);
        }
        return connection;
    }
    @SuppressWarnings("unused")
    public static void closeConnection(Connection connection) throws DaoException{
        try {
            connection.close();
        } catch (SQLException exception) {
            log.warn("Couldn't close connection ", exception);
            throw new DaoException(exception.getMessage(), exception);
        }
    }
    @SuppressWarnings("unused")
    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                log.warn("Couldn't close statement ", exception);
            }
        }
    }
    public static void commit(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException exception) {
                log.warn("Couldn't commit ", exception);
                throw new DaoException(exception.getMessage(), exception);
            }
        }
    }

    public static void rollback(Connection connection, Savepoint savepoint) throws DaoException {
        if (connection != null) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException exception) {
                log.warn("Couldn't rollback to savepoint ", exception);
                throw new DaoException(exception.getMessage(), exception);
            }
        }
    }
}
