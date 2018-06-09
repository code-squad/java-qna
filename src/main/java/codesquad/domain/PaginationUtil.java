package codesquad.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaginationUtil {
    private static final int BLOCK_START_NO = 0;
    private static final int BLOCK_END_NO = 1;
    private static final int PAGE_BLOCK_SIZE = 5;
    private static final int FIRST = 1;

    public static int getBlockStartPage(int pageBlockSize, int currentPage) {
        return ((currentPage - 1) / pageBlockSize) * pageBlockSize + 1;
    }

    public static int getBlockEndPage(int pageBlockSize, int totalPages, int startPage) {
        int endPage = startPage + pageBlockSize - 1;
        if (endPage < totalPages) {
            return endPage;
        }
        return totalPages;
    }

    public static boolean isFirstBlock(int currentPage) {
        return currentPage == FIRST;
    }

    public static int getBlockNumberCurrentPage(int currentPage, int blockSize) {
        return (int)Math.ceil((currentPage - 1) / blockSize) + 1;
    }

    public static List<Integer> getCurrentBlockPageList(int currentPage, int totalPages) {
        List<Integer> pageList = new ArrayList<>();
        pageList.add(getBlockStartPage(PAGE_BLOCK_SIZE, currentPage));
        pageList.add(getBlockEndPage(PAGE_BLOCK_SIZE, totalPages, pageList.get(0)));
        if (pageList.size() > 2) {
            throw new IllegalStateException("page start, end list is over 2");
        }
        return pageList;
    }

    public static String getCurrentPageBlockHTML(int currentPage, int totalPages) {
        List<Integer> pageList = getCurrentBlockPageList(currentPage, totalPages);
        int startNo = pageList.get(BLOCK_START_NO);
        int endNo = pageList.get(BLOCK_END_NO);
        StringBuilder sb = new StringBuilder();
        for (int i = startNo; i <= endNo; i++) {
            sb.append("<li><a href=\"/?page=");
            sb.append(i-1);
            sb.append("\">");
            sb.append(i);
            sb.append("</a></li>");
        }
        return sb.toString();
    }
}
