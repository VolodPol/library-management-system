package org.project.services.pagination;

public abstract class DataForPagination {
    protected int page = 1;

    public DataForPagination(String page) {
        if (page != null)
            this.page = Integer.parseInt(page);
    }

    protected int getPage() {
        return page;
    }
}
