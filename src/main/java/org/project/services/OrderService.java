package org.project.services;


import org.project.commands.RequestContent;
import org.project.dao.CheckoutDao;
import org.project.entity.impl.Book;
import org.project.entity.impl.Checkout;
import org.project.exceptions.DaoException;
import org.project.services.resources.MessageName;


import java.util.List;

/**
 * OrderService class is meant to check if there is enough
 * book copies to order
 */
public class OrderService {
    /**
     * CheckoutDao instance
     */
    private final CheckoutDao dao;

    /**
     * The constructor with single parameter
     * @param dao CheckoutDao object to work with
     */
    public OrderService(CheckoutDao dao) {
        this.dao = dao;
    }

    /**
     * Extracts all books by id and check the amount
     * of spare copies.
     * @param book entity of DB book
     * @param content wrapper of HttpRequest's and HttpSession's content {@link RequestContent}
     * @return boolean to confirm order verification
     * @throws DaoException which may occur in dao
     */
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
