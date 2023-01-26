package org.project.dao;

import org.project.connection.DataSource;
import org.project.entity.Book;
import org.project.entity.Checkout;
import org.project.entity.OrderStatus;
import org.project.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.project.dao.Constants.*;

public class CheckoutDao {
    public List<Checkout> getAllCheckouts() {
        List<Checkout> checkouts = new ArrayList<>();
        try (Connection con = DataSource.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(GET_ALL_CHECKOUTS);
            getOrderBy(checkouts, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkouts;
    }

    public List<Checkout> getCheckoutsByLogin(String login) {
        List<Checkout> checkouts = new ArrayList<>();
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(GET_CHECKOUTS_BY_LOGIN);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            getOrderBy(checkouts, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return checkouts;
    }



    public boolean createCheckout(User user, Book book, Timestamp start, Timestamp end) {
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

    public boolean confirmCheckout(int id) {
        try (Connection con = DataSource.getConnection()) {
            con.setAutoCommit(false);
            Savepoint save = con.setSavepoint("Savepoint");

            try (PreparedStatement ps = con.prepareStatement(UPDATE_CHECKOUT_BY_ID)) {
                ps.setInt(1, id);
                ps.execute();

                con.commit();
            } catch (SQLException exception) {
                con.rollback(save);
                exception.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void deleteCheckout(int id) {

    }
    private void getOrderBy(List<Checkout> checkouts, ResultSet rs) throws SQLException {
        while (rs.next()) {
            Checkout currentCheck = new Checkout();

            currentCheck.setId(rs.getInt("c.id"));
            currentCheck.setStartTime(rs.getTimestamp("start_time"));
            currentCheck.setEndTime(rs.getTimestamp("end_time"));
            currentCheck.setReturned(rs.getByte("is_returned"));
            currentCheck.setStatus(rs.getByte("order_status") == 0 ? OrderStatus.UNCONFIRMED : OrderStatus.CONFIRMED);
            currentCheck.setUser(
                    new UserDaoImpl().findUser(rs.getString("login"))
            );
            currentCheck.setBook(
                    new BookDao().findBook(rs.getString("isbn"))
            );
            checkouts.add(currentCheck);
        }
    }
}
