package org.project.dao;

import org.project.connection.DataSource;
import org.project.entity.Role;
import org.project.entity.Subscription;
import org.project.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.project.dao.Constants.*;

public class UserDaoImpl implements UserDao{
    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = DataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(GET_ALL_USERS);
            while (rs.next()) {
                User user = new User();

                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setFirstName(rs.getString(5));
                user.setSurname(rs.getString(6));
                user.setPhoneNumber(rs.getString(7));
                user.setAge(rs.getInt(8));
                user.setFinedStatus(rs.getByte(9));
                user.setStatus(rs.getByte(10));
                user.setRole(Role.valueOf(rs.getString(11).toUpperCase()));
                user.setSubscription(Subscription.valueOf(rs.getString(12).toUpperCase()));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findUser(String login) {
        User user = new User();
        // Step 1: Establishing a Connection
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setSurname(rs.getString("surname"));
                user.setPhoneNumber(rs.getString("phonenumber"));
                user.setAge(rs.getInt("age"));
                user.setFinedStatus(rs.getByte("fined_status"));
                user.setStatus(rs.getByte("status"));
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                user.setSubscription(Subscription.valueOf(rs.getString("subscription").toUpperCase()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public boolean updateUser(int id) {
        return false;
    }

    @Override
    public boolean insertUser(User user) {
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USER)
        ) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getFirstName());
            preparedStatement.setString(5, user.getSurname());
            preparedStatement.setString(6, user.getPhoneNumber());
            preparedStatement.setInt(7, user.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean deleteUser(int id) {
        try (Connection connection = DataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(DELETE_USER)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
