package codesquad.domain;

public class PaginationUtil {
    public static int getStartpage(int totalPages, int currentPage) {
        return (int)((((double)currentPage-1)/(double)totalPages)*(double)totalPages+1);
    }



    public static int getEndPage(int pageSize, int totalPages, int startPage) {
        int endPage = startPage + pageSize - 1;
        if (endPage < totalPages) {
            return endPage;
        }
        return totalPages;
    }
}
