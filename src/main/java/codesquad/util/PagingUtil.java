package codesquad.util;

import codesquad.question.Question;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PagingUtil {
    private static final int DEFAULT_PAGE_BAR_SIZE = 5;
    public static final int pageSize = 3;

    private int firstNoInPageBar;
    private int lastNoInPageBar;
    private int totalPage;

    public static PagingUtil of(Page<Question> currentPage) {
        //Page.getNumber is zero base
        return new PagingUtil(currentPage.getTotalPages(), currentPage.getNumber() + 1);
    }

    public PagingUtil(int totalPage, int currentPageNo) {
        this.totalPage = totalPage;
        calcBothEndsNumber(currentPageNo);
    }

    private void calcBothEndsNumber(int currentPageNo) {
        this.lastNoInPageBar = ((currentPageNo - 1) / DEFAULT_PAGE_BAR_SIZE + 1) * DEFAULT_PAGE_BAR_SIZE;
        this.firstNoInPageBar = lastNoInPageBar - DEFAULT_PAGE_BAR_SIZE + 1;
    }

    public boolean isPrevButton() {
        return firstNoInPageBar != 1;
    }

    public boolean isNextButton() {
        return totalPage > lastNoInPageBar;
    }

    public List<Integer> getPagingNumbers() {
        List<Integer> pagingNumbers = new ArrayList<>();
        for (int i = firstNoInPageBar; i <= lastNoInPageBar && i <= totalPage; i++) {
            pagingNumbers.add(i);
        }
        return pagingNumbers;
    }

    public int getNumberOfPrevButton() {
        return firstNoInPageBar - 1;
    }

    public int getNumberOfNextButton() {
        return lastNoInPageBar + 1;
    }

    @Override
    public String toString() {
        return "PagingUtil{" +
                "firstNoInPageBar=" + firstNoInPageBar +
                ", lastNoInPageBar=" + lastNoInPageBar +
                ", totalPage=" + totalPage +
                '}';
    }
}
