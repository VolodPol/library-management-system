package org.project.dao;

import org.project.connection.ConnectionManager;
import org.project.connection.DataSource;
import org.project.entity.Role;
import org.project.entity.Subscription;
import org.project.entity.User;
import org.project.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.project.dao.Constants.*;

public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private int numOfRecs;

    public List<User> getAll(int offSet, int total, Role role) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(GET_ALL_USERS_LIMIT);
            ps.setString(1, role.getRoleValue());
            ps.setInt(2, offSet);
            ps.setInt(3, total);
            ResultSet rs = ps.executeQuery();

            User user;
            while (rs.next()) {
                user = new User();
                extractUser(rs, user);
                users.add(user);
            }
            rs.close();
            ResultSet newRs = ps.executeQuery("SELECT FOUND_ROWS()");
            if (newRs.next()) this.numOfRecs = newRs.getInt(1);
            newRs.close();
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return users;
    }

    public int getNumOfRecs() {
        return this.numOfRecs;
    }

    public User findUser(String login) throws DaoException {
        User user = new User();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                extractUser(rs, user);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return user;
    }

    public void setFineAmount(int id, int fine) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            connection.setAutoCommit(false);
            Savepoint save = connection.setSavepoint("Save");

            try (PreparedStatement statement = connection.prepareStatement(FINE_AMOUNT)) {
                statement.setInt(1, fine);
                statement.setInt(2, id);
                statement.executeUpdate();
                connection.commit();

            } catch (SQLException e) {
                ConnectionManager.rollback(connection, save);
            }
        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException(exception.getMessage(), exception.getCause());
        }
    }

    public void blockUser(int id, boolean block) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            con.setAutoCommit(false);
            Savepoint save = con.setSavepoint("Save");

            try (PreparedStatement ps = con.prepareStatement(BLOCK_USER)) {
                ps.setInt(1, block ? 1 : 0);
                ps.setInt(2, id);
                ps.executeUpdate();
                con.commit();

            } catch (SQLException exception) {
                ConnectionManager.rollback(con, save);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
    }

    public void insertUser(User user) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)
        ) {
            fillStatement(preparedStatement, user);
            preparedStatement.setString(8, "user");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
    }

    public boolean deleteUser(int id) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            connection.setAutoCommit(false);
            Savepoint sp = connection.setSavepoint("Savepoint");

            try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
                statement.setInt(1, id);
                statement.executeUpdate();
                connection.commit();

            } catch (SQLException exception) {
                ConnectionManager.rollback(connection, sp);
                exception.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return true;
    }

    public void insertLibrarian(User user) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)
        ) {
            fillStatement(preparedStatement, user);
            preparedStatement.setString(8, "librarian");

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }

    }

    private void fillStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getLogin());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setString(3, user.getEmail());
        preparedStatement.setString(4, user.getFirstName());
        preparedStatement.setString(5, user.getSurname());
        preparedStatement.setString(6, user.getPhoneNumber());
        preparedStatement.setInt(7, user.getAge());
    }
    private void extractUser(ResultSet rs, User user) throws SQLException {
        user.setId(rs.getInt(1));
        user.setLogin(rs.getString(2));
        user.setPassword(rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setFirstName(rs.getString(5));
        user.setSurname(rs.getString(6));
        user.setPhoneNumber(rs.getString(7));
        user.setAge(rs.getInt(8));
        user.setFineAmount(rs.getInt(9));
        user.setStatus(rs.getByte(10));
        user.setRole(Role.valueOf(rs.getString(11).toUpperCase()));
        user.setSubscription(Subscription.valueOf(rs.getString(12).toUpperCase()));
    }
}
