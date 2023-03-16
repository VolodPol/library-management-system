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
    private final CheckoutDao dao;

    public FineService(CheckoutDao dao) {
        this.dao = dao;
    }
    //checkOrders() повертає кількість штрафів (заборгованостей)
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
                throw new RuntimeException(exception);
            }
        }
        return fineSum;
    }
    // Actual - поточний, last - останній запис з кукі
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
        if (actualNum * 50 > oldAmount)
            return actualNum * 50 - oldAmount;

        return (actualNum - lastRec) * 50;
    }
}
