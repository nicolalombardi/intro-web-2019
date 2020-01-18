package com.icecoldbier.utils.pagination;

import com.icecoldbier.utils.Utils;

public class Pagination {
    private static final int DEFAULT_PAGE_SIZE = 15;
    private static final int MAX_PAGE_SIZE = 30;
    private static final int MIN_PAGE_SIZE = 10;

    public static PaginationParameters getPageParameters(String requestedPage, String requestedPageSize, long rowsCount){
        int page = 1;
        int pageSize = DEFAULT_PAGE_SIZE;


        //Grab the requested page size value if it exists, set a default value (1) if it does not
        if(requestedPageSize != null && !requestedPageSize.equals("")){
            pageSize = Integer.parseInt(requestedPageSize);
        }

        pageSize = Utils.coerceInt(MIN_PAGE_SIZE, MAX_PAGE_SIZE, pageSize);


        //Grab the requested page value if it exists, set a default value (1) if it does not
        if(requestedPage != null && !requestedPage.equals("")){
            page = Integer.parseInt(requestedPage);
        }


        int pagesCount = (int)Math.ceil(((rowsCount * 1.0f)/ pageSize));
        page = pagesCount == 0 ? 1 : Utils.coerceInt(1, pagesCount, page);

        return new PaginationParameters(page, pageSize, pagesCount);
    }

}


