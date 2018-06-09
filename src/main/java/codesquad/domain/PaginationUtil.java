package codesquad.domain;

import java.util.ArrayList;
import java.util.List;

public class PaginationUtil {
    private static final int BLOCK_START_INDEX = 0;
    private static final int BLOCK_END_INDEX = 1;
    private static final int BLOCK_SIZE = 5;
    private static final int FIRST = 1;
    private static final String EMPTY = "";
    private static final String HEADER = "<li><a href=\"/?page=";
    private static final String HEADER_ACTIVE = "<li class=\"active\"><a href=\"/?page=";
    private static final String RIGHT_CLOSER = "\">»</a></li>";
    private static final String LEFT_CLOSER = "\">«</a></li>";

    public static int getBlockStartPage(int currentPage) {
        return ((currentPage - 1) / BLOCK_SIZE) * BLOCK_SIZE + 1;
    }

    public static int getBlockEndPage(int totalPages, int startPage) {
        int endPage = startPage + BLOCK_SIZE - 1;
        if (endPage < totalPages) {
            return endPage;
        }
        return totalPages;
    }

    public static boolean isFirstBlock(int currentPage) {
        return getCurrentPageBlockNumber(currentPage) == FIRST;
    }

    public static boolean isLastBlock(int currentBlockNo, int totalPages) {
        return getLastBlockNo(totalPages) == currentBlockNo;
    }

    public static int getLastBlockNo(int totalPages) {
        if (totalPages % BLOCK_SIZE == 0) {
            return totalPages / BLOCK_SIZE;
        }
        return (totalPages / BLOCK_SIZE) + 1;
    }

    public static int getCurrentPageBlockNumber(int currentPage) {
        return (int) Math.ceil((currentPage - 1) / BLOCK_SIZE) + 1;
    }

    public static List<Integer> getCurrentBlockPageList(int currentPage, int totalPages) {
        List<Integer> pageList = new ArrayList<>();
        pageList.add(getBlockStartPage(currentPage));
        pageList.add(getBlockEndPage(totalPages, pageList.get(0)));
        if (pageList.size() > 2) {
            throw new IllegalStateException("page start, end list is over 2");
        }
        return pageList;
    }

    public static String getCurrentPageBlockHTML(int currentPage, int totalPages) {
        List<Integer> pageList = getCurrentBlockPageList(currentPage, totalPages);
        int startNo = pageList.get(BLOCK_START_INDEX);
        int endNo = pageList.get(BLOCK_END_INDEX);

        StringBuilder sb = new StringBuilder();
        sb.append(PaginationUtil.getBlockLeft(startNo, currentPage));
        sb.append(PaginationUtil.getBlockBody(startNo, endNo, currentPage));
        sb.append(PaginationUtil.getBlockRight(endNo, currentPage, totalPages));

        return sb.toString();
    }

    public static String getBlockLeft(int startNo, int currentPage) {
        if (PaginationUtil.isFirstBlock(currentPage)) {
            return EMPTY;
        }
        return HEADER + (startNo - 2) + LEFT_CLOSER;
    }

    public static String getBlockRight(int endNo, int currentPage, int totalPages) {
        if (PaginationUtil.isLastBlock(getCurrentPageBlockNumber(currentPage), totalPages)) {
            return EMPTY;
        }
        return HEADER + endNo + RIGHT_CLOSER;
    }

    public static String getBlockBody(int startNo, int endNo, int currentPage) {
        StringBuilder sb = new StringBuilder();
        for (int i = startNo; i <= endNo; i++) {
            if (i == currentPage) {
                sb.append(HEADER_ACTIVE);
            }
            if (i != currentPage) {
                sb.append(HEADER);
            }
            // pageable에서는 0페이지부터 시작하므로 i-1 해줘서 페이지 넘버를 보정한다.
            sb.append(i - 1);
            sb.append("\">");
            sb.append(i);
            sb.append("</a></li>");
        }
        return sb.toString();
    }
}
