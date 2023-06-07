package com.library.services.pagination;

/**
 * Abstract class for storing pagination data
 */
public abstract class DataForPagination {
    /**
     * Current page by default
     */
    protected int page = 1;

    public DataForPagination(String page) {
        if (page != null)
            this.page = Integer.parseInt(page);
    }

    protected int getPage() {
        return page;
    }
}
