package org.project.services;

import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.sorting.OrderType;
import org.project.entity.sorting.Sorting;
import org.project.exceptions.DaoException;
import org.project.utils.UtilProvider;

import java.util.List;

public class Pagination {
    private Sorting sorting;
    private OrderType type;

    private int currentPage;
    private static final int recsPerPage = 5;

    private int numberOrRecords;
    private int numberOfPages;

    {
        currentPage = 1;
        sorting = Sorting.ASC;
        type = OrderType.DEFAULT;
    }

    public Pagination (String page, String type, String sorting) {
        if (!UtilProvider.isEmpty(page))
            currentPage = Integer.parseInt(page);
        if (!UtilProvider.isEmpty(type))
            this.type = OrderType.valueOf(type.toUpperCase());
        if (!UtilProvider.isEmpty(sorting))
            this.sorting = Sorting.valueOf(sorting.toUpperCase());
    }

    public List<Book> provideData() throws DaoException {
        BookDao dao = new BookDao();
        List<Book> books = dao.findAll((currentPage - 1) * recsPerPage, recsPerPage, sorting, type);
        numberOrRecords = dao.getNumOfRecs();
        numberOfPages = calcNumOfPages();
        return books;
    }

    private int calcNumOfPages() {
        return (int) Math.ceil((double) numberOrRecords / numberOfPages);
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
