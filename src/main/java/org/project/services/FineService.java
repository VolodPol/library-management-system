package org.project.services;

import jakarta.servlet.http.Cookie;
import org.project.dao.CheckoutDao;
import org.project.entity.impl.Checkout;
import org.project.entity.impl.User;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;

import java.sql.Timestamp;
import java.util.List;

/**
 * The class for calculating fines for the user
 */
public class FineService {
    /**
     * CheckoutDao instance
     */
    private final CheckoutDao dao;
    /**
     * The fine
     */
    private static final int FINE = 50;

    /**
     * Single-argument constructor to init dao
     * @param dao CheckoutDao obj to work with
     */
    public FineService(CheckoutDao dao) {
        this.dao = dao;
    }
    //checkOrders() повертає кількість штрафів (заборгованостей)

    /**
     * Calculates the number of fines and sets the fine status of an order,
     * by comparing users' checkouts date to bring back with the current time.
     * @param user the user entity
     * @return integer for the amount of fines
     * @throws DaoException which may occur in dao
     */
    public int checkOrders(User user) throws DaoException {
        int fineSum = 0;

        String login = user.getLogin();
        List<Checkout> orders = dao.findAllByLogin(login);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (Checkout order : orders) {
            if (now.after(order.getEndTime())) {
                order.setFinedStatus((byte) 1);
                fineSum++;
            }
            else
                order.setFinedStatus((byte) 0);
            try {
                dao.setFineStatus(order.getId(), order.getFinedStatus());
            } catch (DaoException exception) {
                throw new DaoException("Dao exception caught in service layer", exception);
            }
        }
        return fineSum;
    }
    /**
     * The method's purpose is to calculate the fine amount of user by checking cookies with last number of fines and
     * the user's fine amount in DB. If there were no cookies or the last recorded number of fines and the fine sum of
     * user is zero - return no additional fine. If the current number of fined orders multiplied by the fine (50) is
     * bigger than the old fine amount, then return the difference. Otherwise, return the difference between the actual
     * and last number of fines, multiplied on fine amount (50).
     * @param username user's name
     * @param cookies cookie files to last fines
     * @param actualNum the currently checked num of fines
     * @param oldAmount the user's fine sum
     * @return int - the amount of fine to add
     */
    public int calculateFine(String username, Cookie[] cookies, int actualNum, int oldAmount) {
        boolean isCookiesEmpty = true;

        int lastRec = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(UtilProvider.getFineCookie(username)) && !cookie.getValue().isEmpty()) {
                lastRec = Integer.parseInt(cookie.getValue());
                isCookiesEmpty = false;
            }
        }
        if (isCookiesEmpty || (lastRec == 0 && oldAmount > 0))
            return 0;
        if (actualNum * FINE > oldAmount)
            return actualNum * FINE - oldAmount;

        return (actualNum - lastRec) * FINE;
    }
}
