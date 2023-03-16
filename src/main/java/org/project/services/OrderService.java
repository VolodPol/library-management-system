package org.project.services;


import org.project.commands.RequestContent;
import org.project.dao.CheckoutDao;
import org.project.entity.Book;
import org.project.entity.Checkout;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;


import java.util.List;

public class OrderService {
    private final CheckoutDao dao;
    public OrderService(CheckoutDao dao) {
        this.dao = dao;
    }

    public boolean verifyOrder(Book book, RequestContent content)
            throws DaoException {
        List<Checkout> list = dao.findAllByBookId(book.getId());

        int orderSum = list.size();
        if (orderSum >= book.getCopiesNumber()) {
            content.setRequestAttribute("error", MessageName.NOT_AVAILABLE);
            return false;
        }
        return true;
    }
}
