package org.project.services;

import jakarta.servlet.http.Cookie;
import org.project.dao.CheckoutDao;
import org.project.entity.Checkout;
import org.project.entity.User;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;

import java.sql.Timestamp;
import java.util.List;

public class FineService {
    private final static CheckoutDao dao;

    static {
        dao = new CheckoutDao();
    }
    //checkOrders() повертає кількість штрафів (заборгованостей)
    public static int checkOrders(User user) throws DaoException {
        int fineSum = 0;

        String login = user.getLogin();
        List<Checkout> orders = dao.findCheckoutsByLogin(login);
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
                throw new RuntimeException(exception);
            }
        }
        return fineSum;
    }
    // Actual - поточний, last - останній запис з кукі
    public static int calculateFine(String username, Cookie[] cookies, int actualNum, int oldAmount) {
        int lastRec = 0;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(UtilProvider.getFineCookie(username)) && !cookie.getValue().isEmpty()) {
                lastRec = Integer.parseInt(cookie.getValue());
            }
        }
        if (lastRec == 0 && oldAmount > 0) return 0;
        return (actualNum - lastRec) * 50;
    }
}
