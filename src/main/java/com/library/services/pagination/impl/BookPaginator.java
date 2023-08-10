package com.library.services.pagination.impl;

import com.library.commands.RequestContent;
import com.library.dao.BookDao;
import com.library.entity.Book;
import com.library.entity.sorting.SortBy;
import com.library.entity.sorting.SortOrder;
import com.library.exceptions.DaoException;
import com.library.services.pagination.DataForPagination;
import com.library.services.pagination.Paginator;
import lombok.Getter;

import java.util.List;

/**
 * Class descendant of Paginator class with the generic of Book class
 */
public class BookPaginator extends Paginator<Book> {
    private final BookDao bookDao;

    public BookPaginator() {
        this.bookDao = new BookDao();
    }
    public BookPaginator(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    /**
     * Static successor of DataForPagination class to store pagination data
     */
    private static class ParamData extends DataForPagination {
        /**
         * Sorting order by default
         */
        private SortOrder sortOrder = SortOrder.ASC;
        /**
         * Sorting by default
         */
        private SortBy sortBy = SortBy.DEFAULT;
        /**
         * The default value of records per page
         */
        @Getter
        private int recsPerPage = 5;
        /**
         * Default sorting id from UI
         */
        private final static String defaultSorting = "defaultSorting";
        /**
         * Default type id from UI
         */
        private final static String defaultType = "defaultType";

        /**
         * Constructor to initialize fields for pagination
         * @param page current page
         * @param sortOrder sorting order
         * @param sortBy sort by
         * @param recsPerPage records per page
         */
        public ParamData(String page, String sortOrder, String sortBy, String recsPerPage) {
            super(page);
            if (sortOrder != null && !sortOrder.equals(defaultSorting))
                this.sortOrder = SortOrder.valueOf(sortOrder.toUpperCase());
            if (sortBy != null && !sortBy.equals(defaultType))
                this.sortBy = SortBy.valueOf(sortBy.toUpperCase());
            if (recsPerPage != null)
                this.recsPerPage = Integer.parseInt(recsPerPage);
        }

        @Override
        protected int getPage() {
            return super.getPage();
        }

        private SortOrder getSortOrder() {
            return sortOrder;
        }
        private SortBy getSortBy() {
            return sortBy;
        }
    }

    /**
     * Implementation of provideData() method {@link Paginator#provideData(RequestContent)}
     * which build paramData from RequestContent, set pagination attributes, retrieves records
     * from DB and assigns fields for pagination of Paginator class {@link Paginator#provideData(RequestContent)}
     * @param content wrapper of HttpRequest's and HttpSession's content
     * @return list of Book entities
     * @throws DaoException which may occur in dao
     */
    @Override
    public List<Book> provideData(RequestContent content) throws DaoException {
        ParamData data = new ParamData(
                content.getParameter("page"),
                content.getParameter("sortOrder"),
                content.getParameter("sortBy"),
                content.getParameter("recNum")
        );
        super.recordsPerPage = data.getRecsPerPage();
        setPaginationAttributes(content, data);
        List<Book> books = bookDao.findAll(
                (data.getPage() - 1) * data.getRecsPerPage(),
                data.getRecsPerPage(),
                data.getSortOrder(),
                data.getSortBy()
        );
        super.numberOrRecords = bookDao.getNumOfRecs();
        super.numberOfPages = calcNumOfPages();
        super.currentPage = data.getPage();
        super.recordsPerPage = data.getRecsPerPage();

        return books;
    }

    private void setPaginationAttributes(RequestContent content, ParamData data) {
        content.setRequestAttribute("sortBy", data.getSortBy());
        content.setRequestAttribute("sortOrder", data.getSortOrder());
    }
}