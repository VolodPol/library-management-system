package org.project.connection;

import org.project.exceptions.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

public class ConnectionManager {
    public static Connection getConnection() throws SQLException {
        return DataSource.getConnection();
    }

    public static void closeSafely(Connection connection) throws DaoException{
        try {
            connection.close();
        } catch (SQLException exception) {
            throw new DaoException(exception.getMessage(), exception);
        }
    }

    public static void rollback(Connection connection) throws DaoException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException exception) {
                throw new DaoException(exception.getMessage(), exception);
            }
        }
    }

    public static void rollback(Connection connection, Savepoint savepoint) throws DaoException {
        if (connection != null) {
            try {
                connection.rollback(savepoint);
            } catch (SQLException exception) {
                throw new DaoException(exception.getMessage(), exception);
            }
        }
    }
}
