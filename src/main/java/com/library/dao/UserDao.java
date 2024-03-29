package com.library.dao;

import com.library.connection.ConnectionManager;
import com.library.dao.constants.Queries;
import com.library.entity.Role;
import com.library.entity.Subscription;
import com.library.entity.User;
import com.library.exceptions.DaoException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class UserDao {
    private static final Logger log = LoggerFactory.getLogger(UserDao.class);
    private int numOfRecs;

    public List<User> findAll(int offSet, int total, Role role) throws DaoException {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement ps = connection.prepareStatement(Queries.GET_ALL_USERS_LIMIT)) {
            ps.setString(1, role.getRoleValue());
            ps.setInt(2, offSet);
            ps.setInt(3, total);

            User user;
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    user = extractUser(rs);
                    users.add(user);
                }
            }
            ResultSet newRs = ps.executeQuery("SELECT FOUND_ROWS()");
            if (newRs.next()) this.numOfRecs = newRs.getInt(1);
            newRs.close();
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in UserDao class", e);
        }
        return users;
    }

    public Optional<User> findByLogin(String login) throws DaoException {
        Optional<User> result;
        User user = null;
        try (Connection connection = ConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(Queries.FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    user = extractUser(rs);
                }
            }
            result = Optional.ofNullable(user);
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in UserDao class", e);
        }
        return result;
    }

    public void upgradeSub(int id, int fine) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            boolean auto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            Savepoint save = connection.setSavepoint("Save");

            try (PreparedStatement statement = connection.prepareStatement(Queries.FINE_AMOUNT)) {
                statement.setInt(1, fine);
                statement.setInt(2, id);
                statement.executeUpdate();

                ConnectionManager.commit(connection);
            } catch (SQLException e) {
                ConnectionManager.rollback(connection, save);
            } finally {
                connection.setAutoCommit(auto);
            }
        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException("DaoException occurred in UserDao class", exception);
        }
    }

    public void upgradeSub(String login) throws DaoException {
        try (Connection con = ConnectionManager.getConnection()) {
            boolean auto = con.getAutoCommit();
            con.setAutoCommit(false);
            Savepoint savePoint = con.setSavepoint("SavePoint");

            try (PreparedStatement statement = con.prepareStatement(Queries.UPGRADE)) {
                statement.setString(1, login);
                statement.executeUpdate();

                ConnectionManager.commit(con);
            } catch (SQLException e) {
                ConnectionManager.rollback(con, savePoint);
            } finally {
                con.setAutoCommit(auto);
            }
        } catch (SQLException exception) {
            log.error("dao exception occurred in book dao class: " + exception.getMessage());
            throw new DaoException("DaoException occurred in UserDao class", exception);
        }
    }

    public boolean block(int id, boolean block) throws DaoException {
        boolean result = false;
        try (Connection con = ConnectionManager.getConnection()) {
            boolean auto = con.getAutoCommit();
            con.setAutoCommit(false);
            Savepoint save = con.setSavepoint("Save");

            try (PreparedStatement ps = con.prepareStatement(Queries.BLOCK_USER)) {
                ps.setInt(1, block ? 1 : 0);
                ps.setInt(2, id);

                ps.executeUpdate();
                ConnectionManager.commit(con);
            } catch (SQLException exception) {
                ConnectionManager.rollback(con, save);
            } finally {
                con.setAutoCommit(auto);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in UserDao class", e);
        }
        return result;
    }

    public void insertUser(User user, Role role) throws DaoException {
        try (Connection connection = ConnectionManager.getConnection()) {
            boolean auto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            Savepoint sp = connection.setSavepoint("Save");

            try (PreparedStatement preparedStatement = connection.prepareStatement(Queries.CREATE_USER)) {
                fillStatement(preparedStatement, user);
                preparedStatement.setString(8, role.equals(Role.USER) ? "user" : "librarian");

                preparedStatement.executeUpdate();
                ConnectionManager.commit(connection);
            } catch (SQLException exception) {
                ConnectionManager.rollback(connection, sp);
            } finally {
                connection.setAutoCommit(auto);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in UserDao class", e);
        }
    }

    public boolean delete(int id) throws DaoException {
        boolean result = false;
        try (Connection connection = ConnectionManager.getConnection()) {
            boolean auto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            Savepoint sp = connection.setSavepoint("Savepoint");

            try (PreparedStatement statement = connection.prepareStatement(Queries.DELETE_USER)) {
                statement.setInt(1, id);
                int update = statement.executeUpdate();
                result = update != 0;

                ConnectionManager.commit(connection);
            } catch (SQLException exception) {
                ConnectionManager.rollback(connection, sp);
            } finally {
                connection.setAutoCommit(auto);
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book dao class: " + e.getMessage());
            throw new DaoException("DaoException occurred in UserDao class", e);
        }
        return result;
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
    private User extractUser(ResultSet rs) throws SQLException {
        return new User.UserBuilder()
                .addId(rs.getInt(1))
                .addLogin(rs.getString(2))
                .addPassword(rs.getString(3))
                .addEmail(rs.getString(4))
                .addFirstName(rs.getString(5))
                .addSurname(rs.getString(6))
                .addPhoneNumber(rs.getString(7))
                .addAge(rs.getInt(8))
                .addFineAmount(rs.getInt(9))
                .addStatus(rs.getByte(10))
                .addRole(Role.valueOf(rs.getString(11).toUpperCase()))
                .addSubscription(Subscription.valueOf(rs.getString(12).toUpperCase()))
                .build();
    }
}
