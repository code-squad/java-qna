package codesquad.util;

import java.util.ArrayList;
import java.util.List;

public class PagingUtil {

    private static final int DEFAULT_PAGE_BUNDLE_NUMBER = 5;

    private int currentPage;
    private int maxPage;

    public PagingUtil(int currentPage, int maxPage) {
        this.currentPage = currentPage;
        this.maxPage = maxPage;
    }

    public Integer getNextPage(){
            int nextPage = ((this.currentPage / DEFAULT_PAGE_BUNDLE_NUMBER) + 1) * DEFAULT_PAGE_BUNDLE_NUMBER;
            if(nextPage > this.maxPage) return null;
            return nextPage;
    }

    public Integer getPrePage() {
        int prePage =  ((this.currentPage / DEFAULT_PAGE_BUNDLE_NUMBER) * DEFAULT_PAGE_BUNDLE_NUMBER) - 1;
        if(prePage == -1) return null;
        return prePage;
    }

    public List<Integer> getPages(){
        int startPage = (this.currentPage / DEFAULT_PAGE_BUNDLE_NUMBER) * DEFAULT_PAGE_BUNDLE_NUMBER;
        int lastPage = (((this.currentPage / DEFAULT_PAGE_BUNDLE_NUMBER) + 1) * DEFAULT_PAGE_BUNDLE_NUMBER) - 1;
        lastPage = maxPage < lastPage ? maxPage : lastPage;

        List<Integer> pages = new ArrayList<>();
        for(int i = startPage; i <= lastPage; i++){
            pages.add(i);
        }
        return pages;
    }
}
