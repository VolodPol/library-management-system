package org.project.services.pagination.impl;

import org.project.commands.RequestContent;
import org.project.dao.BookDao;
import org.project.entity.Book;
import org.project.entity.sorting.SortBy;
import org.project.entity.sorting.SortOrder;
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
        private SortOrder sortOrder = SortOrder.ASC;
        private SortBy sortBy = SortBy.DEFAULT;
        private int recsPerPage = 5;

        private final static String defaultSorting = "defaultSorting";
        private final static String defaultType = "defaultType";

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
        public int getRecsPerPage() {
            return recsPerPage;
        }
    }

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