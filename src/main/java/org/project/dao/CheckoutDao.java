package org.project.dao;

import org.project.connection.DataSource;
import org.project.entity.*;
import org.project.exceptions.DaoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.project.dao.Constants.*;

public class CheckoutDao {
    private static final Logger log = LoggerFactory.getLogger(CheckoutDao.class);
    public List<Checkout> getAllCheckouts() throws DaoException {
        List<Checkout> checkouts = new ArrayList<>();
        try (Connection con = DataSource.getConnection()) {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(GET_ALL_CHECKOUTS);
            getOrderBy(checkouts, rs);
        } catch (SQLException e) {
            log.error("dao exception occurred in user dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return checkouts;
    }

    public List<Checkout> getCheckoutsByLogin(String login) throws DaoException{
        List<Checkout> checkouts = new ArrayList<>();
        try (Connection con = DataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(GET_CHECKOUTS_BY_LOGIN);
            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();
            getOrderBy(checkouts, rs);
        } catch (SQLException e) {
            log.error("dao exception occurred in user dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return checkouts;
    }



    public boolean createCheckout(Checkout checkout) throws DaoException {
        try (Connection con = DataSource.getConnection()) {
            con.setAutoCommit(false);
            Savepoint sp = con.setSavepoint("SavePoint");

            try (PreparedStatement ps = con.prepareStatement(CREATE_CHECKOUT)) {
                ps.setTimestamp(1, checkout.getStartTime());
                ps.setTimestamp(2, checkout.getEndTime());
                ps.setString(3, checkout.getType().getValue());
                ps.setInt(4, checkout.getUser().getId());
                ps.setInt(5, checkout.getBook().getId());

                ps.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                try {
                    con.rollback(sp);
                    return false;
                } catch(SQLException exception) {
                    log.error("Couldn't rollback connection to savepoint");
                }
                throw new DaoException(e.getMessage(), e.getCause());
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in book user class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return true;
    }

    public boolean confirmCheckout(int id) throws DaoException {
        try (Connection con = DataSource.getConnection()) {
            con.setAutoCommit(false);
            Savepoint save = con.setSavepoint("Savepoint");

            try (PreparedStatement ps = con.prepareStatement(UPDATE_CHECKOUT_BY_ID)) {
                ps.setInt(1, id);
                ps.execute();

                con.commit();
            } catch (SQLException exception) {
                con.rollback(save);
                log.error("Couldn't rollback connection to savepoint");
                return false;
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in user dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
        return true;
    }

    public void deleteCheckout(int bookId, Timestamp startTime, Timestamp endTime) throws DaoException {
        try (Connection connection = DataSource.getConnection()) {
            connection.setAutoCommit(false);
            Savepoint sp = connection.setSavepoint("Savepoint");

            try (PreparedStatement statement = connection.prepareStatement(DELETE_CHECKOUT)) {
                statement.setInt(1, bookId);
                statement.setTimestamp(2, startTime);
                statement.setTimestamp(3, endTime);
                statement.executeUpdate();
                connection.commit();

            } catch (SQLException exception) {
                connection.rollback(sp);
                log.error("Couldn't rollback connection to savepoint");
            }
        } catch (SQLException e) {
            log.error("dao exception occurred in user dao class: " + e.getMessage());
            throw new DaoException(e.getMessage(), e.getCause());
        }
    }
    private void getOrderBy(List<Checkout> checkouts, ResultSet rs) throws SQLException, DaoException {
        while (rs.next()) {
            Checkout currentCheck = new Checkout();

            currentCheck.setId(rs.getInt("c.id"));
            currentCheck.setStartTime(rs.getTimestamp("start_time"));
            currentCheck.setEndTime(rs.getTimestamp("end_time"));
            currentCheck.setIsReturned(rs.getByte("is_returned"));
            currentCheck.setOrderStatus(rs.getByte("order_status") == 0 ? OrderStatus.UNCONFIRMED : OrderStatus.CONFIRMED);
            currentCheck.setType(rs.getString("c.type").equals("subscription") ? Type.SUBSCRIPTION : Type.READING_ROOM);
            try {
                currentCheck.setUser(
                        new UserDao().findUser(rs.getString("login"))
                );
                currentCheck.setBook(
                        new BookDao().findBook(rs.getString("isbn"))
                );
            } catch (DaoException exception) {
                log.error("dao exception occurred in book dao class: " + exception.getMessage());
                throw new DaoException(exception.getMessage(), exception.getCause());
            }
            checkouts.add(currentCheck);
        }
    }
}
