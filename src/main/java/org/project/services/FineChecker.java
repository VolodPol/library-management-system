package org.project.services;

import org.project.dao.CheckoutDao;
import org.project.entity.Checkout;
import org.project.entity.User;
import org.project.exceptions.DaoException;

import java.sql.Timestamp;
import java.util.List;
/*
checkOrders() повертає кількість штрафів (заборгованостей)
 */
public class FineChecker {
    private final static CheckoutDao checker;
    static {
        checker = new CheckoutDao();
    }

    //створити поле 'теперішній час' й ініціалізувати його в статікі через лістенер кож годину
    public static int checkOrders(User user) throws DaoException {
        int fineSum = 0;

        String login = user.getLogin();
        List<Checkout> orders = checker.getCheckoutsByLogin(login);
        Timestamp now = new Timestamp(System.currentTimeMillis());
        for (Checkout order : orders) {
            if (now.after(order.getEndTime())) {
                order.setFinedStatus((byte) 1);
                fineSum++;
            }
            else
                order.setFinedStatus((byte) 0);
            try {
                checker.setFineStatus(order.getId(), order.getFinedStatus());
            } catch (DaoException exception) {
                throw new RuntimeException(exception);
            }
        }
        return fineSum;
    }
}