package codesquad.util;

import java.util.ArrayList;
import java.util.List;

public class PageUtil {
    public static final int DEFAULF_PAGE_SIZE = 15;
    public static final int DEFAULT_PAGES_COUNT = 5;

    private int totalPageNum;
    private int currentPageNum;

    public PageUtil(int totalPageNum, int currentPageNum) {
        this.currentPageNum = currentPageNum;
        this.totalPageNum = totalPageNum;

    }

    public List<Integer> getPages() {
        List<Integer> pages = new ArrayList<>();
        int first = DEFAULT_PAGES_COUNT * ((currentPageNum - 1) / DEFAULT_PAGES_COUNT) + 1;
        int last = (first + DEFAULT_PAGES_COUNT - 1) < totalPageNum ? first + DEFAULT_PAGES_COUNT - 1 : totalPageNum;

        for (int i = first; i <= last; i++) {
            pages.add(i);
        }
        return pages;
    }

    public Integer getPreFirstPage() {
        // 0~4 사이면 null 값 리턴해야 함
        if (1 <= currentPageNum && currentPageNum <= DEFAULT_PAGES_COUNT)
            return null;
        return DEFAULT_PAGES_COUNT * ((currentPageNum - 1) / DEFAULT_PAGES_COUNT) - DEFAULT_PAGES_COUNT + 1;
    }

    public Integer getNextFirstPage() {
        // totalPageNum이 있는 영역의 첫번째 값보다 크거나 같으면 null값을 리턴해야함
        if (currentPageNum >= (DEFAULT_PAGES_COUNT * (totalPageNum / DEFAULT_PAGES_COUNT)) - 4)
            return null;
        return DEFAULT_PAGES_COUNT * (currentPageNum / DEFAULT_PAGES_COUNT) + DEFAULT_PAGES_COUNT + 1;
    }

}
