package org.project.dao;

import org.project.connection.DataSource;
import org.project.entity.Book;
import org.project.entity.User;

import java.sql.*;

import static org.project.dao.Constants.*;

public class OrderDao {
    public boolean createOrder(User user, Book book, Timestamp start, Timestamp end) {
        try (Connection con = DataSource.getConnection()) {
            con.setAutoCommit(false);
            Savepoint sp = con.setSavepoint("SavePoint");

            try (PreparedStatement ps = con.prepareStatement(CREATE_CHECKOUT)) {
                ps.setTimestamp(1, start);
                ps.setTimestamp(2, end);
                ps.setInt(3, user.getId());
                ps.setInt(4, book.getId());

                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback(sp);
                    return false;
                } catch(SQLException exception) {
                    exception.printStackTrace();
                    //log here
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
