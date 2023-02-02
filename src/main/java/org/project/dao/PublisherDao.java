package org.project.dao;

import org.project.connection.DataSource;
import org.project.entity.Publisher;

import java.sql.*;

public class PublisherDao {
    private final static String QUERY = "SELECT * FROM publisher WHERE name = ?";
    private final static String INSERT = "INSERT INTO publisher (name) VALUE (?)";


    public boolean isPresent(String name) {
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement statement = con.prepareStatement(QUERY);
            statement.setString(1, name);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Publisher findPublisher(String name) {
        Publisher publisher = new Publisher();
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(QUERY);
            ps.setString(1, name);
            ResultSet resultSet = ps.executeQuery();

            if (resultSet.next()) {
                publisher.setId(resultSet.getInt(1));
                publisher.setName(resultSet.getString(2));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return publisher;
    }

    public void insertPublisher(Publisher publisher) {
        try (Connection con = DataSource.getConnection()) {
            con.setAutoCommit(false);
            Savepoint sp = con.setSavepoint("Save");

            try (PreparedStatement ps = con.prepareStatement(INSERT)) {
                ps.setString(1, publisher.getName());

                ps.execute();
                con.commit();
            } catch (SQLException e) {
                con.rollback(sp);
                e.printStackTrace();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


}
