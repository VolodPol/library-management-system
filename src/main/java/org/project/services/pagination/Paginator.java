package org.project.services.pagination;

import org.project.commands.RequestContent;
import org.project.entity.Entity;
import org.project.exceptions.DaoException;

import java.util.List;

public abstract class Paginator<E extends Entity> {
    protected int numberOrRecords;
    protected int numberOfPages;
    protected int currentPage;

    public abstract List<E> provideData(RequestContent content) throws DaoException;
    public final int getNumberOfPages() {
        return numberOfPages;
    }
    public final int getCurrentPage() {
        return currentPage;
    }
    protected int calcNumOfPages() {
        return (int) Math.ceil((double) numberOrRecords / 5);
    }
}
