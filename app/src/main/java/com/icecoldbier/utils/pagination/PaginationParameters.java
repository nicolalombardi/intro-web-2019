package com.icecoldbier.utils.pagination;

public class PaginationParameters {
    private int page;
    private int pageSize;
    private int pagesCount;

    public PaginationParameters(int page, int pageSize, int pagesCount) {
        this.page = page;
        this.pageSize = pageSize;
        this.pagesCount = pagesCount;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getPagesCount() {
        return pagesCount;
    }
}
