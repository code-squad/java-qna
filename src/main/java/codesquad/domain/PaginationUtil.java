package codesquad.domain;

public class PaginationUtil {
    private static final int PAGE_BLOCK_SIZE = 5;
    private static final int FIRST = 1;

    public static int getBlockStartPage(int pageBlockSize, int currentPage) {
        return ((currentPage - 1) / pageBlockSize) * pageBlockSize + 1;
    }

    public static int getEndPage(int pageBlockSize, int totalPages, int startPage) {
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
}
