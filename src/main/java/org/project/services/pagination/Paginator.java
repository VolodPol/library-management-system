package org.project.services.pagination;

import org.project.commands.RequestContent;
import org.project.entity.Entity;
import org.project.exceptions.DaoException;

import java.util.List;

/**
 * Abstract bare class for entity pagination
 * @param <E> the inheritors of Entity
 */
public abstract class Paginator<E extends Entity> {
    /**
     * Number of records from DB
     */
    protected int numberOrRecords;
    /**
     * Number of pages to visit
     */
    protected int numberOfPages;
    /**
     * Number of the current page
     */
    protected int currentPage;
    /**
     * Records to display per single page
     */
    protected int recordsPerPage;

    /**
     * The method provides list of E generic type objects
     * @param content wrapper of HttpRequest's and HttpSession's content
     * @return list of E objects
     * @throws DaoException which may occur in dao
     */
    public abstract List<E> provideData(RequestContent content) throws DaoException;

    /**
     * Calculates the number of pages by considering number of records and records per page
     * @return int quantity of pages
     */
    protected int calcNumOfPages() {
        return (int) Math.ceil((double) numberOrRecords / recordsPerPage);
    }
    public final int getNumberOfPages() {
        return numberOfPages;
    }
    public final int getCurrentPage() {
        return currentPage;
    }
    public final int getRecordsPerPage(){
        return recordsPerPage;
    }
}
