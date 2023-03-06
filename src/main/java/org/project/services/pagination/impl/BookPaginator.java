package org.project.services.pagination.impl;

import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.sorting.OrderType;
import org.project.entity.sorting.Sorting;
import org.project.exceptions.DaoException;
import org.project.services.pagination.DataForPagination;
import org.project.services.pagination.Paginator;

import java.util.List;

public class BookPaginator extends Paginator<Book> {
    private final BookDao bookDao;

    public BookPaginator() {
        this.bookDao = new BookDao();
    }

    private static class ParamData extends DataForPagination {
        private Sorting sorting = Sorting.ASC;
        private OrderType type = OrderType.DEFAULT;

        public ParamData(String page, String sorting, String type) {
            super(page);
            if (sorting != null)
                this.sorting = Sorting.valueOf(sorting.toUpperCase());
            if (type != null)
                this.type = OrderType.valueOf(type.toUpperCase());
        }

        @Override
        protected int getPage() {
            return super.getPage();
        }

        private Sorting getSorting() {
            return sorting;
        }
        private OrderType getType() {
            return type;
        }
    }

    //5 instead of recsPerPage
    @Override
    public List<Book> provideData(RequestContent content) throws DaoException {
        ParamData data = new ParamData(
                content.getParameter("page"),
                content.getParameter("orderType"),
                content.getParameter("orderBy")
        );
        List<Book> books = bookDao.findAll((data.getPage() - 1) * 5, 5, data.getSorting(), data.getType());

        numberOrRecords = bookDao.getNumOfRecs();
        numberOfPages = calcNumOfPages();
        currentPage = data.getPage();

        return books;
    }
}