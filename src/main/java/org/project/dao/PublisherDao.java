package org.project.dao;

import org.project.connection.ConnectionManager;
import org.project.entity.Publisher;
import org.project.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class PublisherDao {
    private final static Logger log = LoggerFactory.getLogger(PublisherDao.class);

    private final static String QUERY = "SELECT * FROM publisher WHERE name = ?";
    private final static String INSERT = "INSERT INTO publisher (name) VALUE (?)";


    public boolean isPresent(String name) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement statement = con.prepareStatement(QUERY);
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            log.error("DaoException occurred in " + PublisherDao.class);
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return false;
    }

    public Publisher findByName(String name) throws DaoException {
        Publisher publisher = new Publisher();
        try (Connection con = ConnectionManager.getConnection()) {
            PreparedStatement ps = con.prepareStatement(QUERY);
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                publisher.setId(resultSet.getInt(1));
                publisher.setName(resultSet.getString(2));
            }

        } catch (SQLException exception) {
            log.error("DaoException occurred in " + PublisherDao.class);
            throw new DaoException(exception.getMessage(), exception.getCause());
        }
        return publisher;
    }

    public void insert(Publisher publisher) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            con.setAutoCommit(false);
            Savepoint sp = con.setSavepoint("Save");

            try (PreparedStatement ps = con.prepareStatement(INSERT)) {
                ps.setString(1, publisher.getName());

                ps.execute();
                con.commit();
            } catch (SQLException e) {
                ConnectionManager.rollback(con, sp);
                e.printStackTrace();
            }
        } catch (SQLException exception) {
            log.error("DaoException occurred in " + PublisherDao.class);
            throw new DaoException(exception.getMessage(), exception.getCause());
        }
    }
}
