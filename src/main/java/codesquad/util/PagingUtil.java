package codesquad.util;

import codesquad.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class PagingUtil {
    private static final int DEFAULT_PAGE_BAR_SIZE = 5;
    private boolean prevButton;
    private boolean nextButton;
    private List<Integer> pagingNumbers = new ArrayList<>();
    private int numberOfPrevButton;
    private int numberOfNextButton;

    public PagingUtil(int totalPage, int currentPageNo, int pageBarSize) {
        int lastNoInPageBar = ((currentPageNo - 1) / pageBarSize + 1) * pageBarSize;
        this.nextButton = totalPage > lastNoInPageBar;

        int firstNoInPageBar = lastNoInPageBar - pageBarSize + 1;
        this.prevButton = firstNoInPageBar != 1;

        for (int pageNo = firstNoInPageBar; pageNo <= lastNoInPageBar && pageNo <= totalPage; pageNo++) {
            this.pagingNumbers.add(pageNo);
        }

        this.numberOfPrevButton = firstNoInPageBar -1;
        this.numberOfNextButton = lastNoInPageBar + 1;
    }

    public static PagingUtil of(Page<Question> currentPage) {
        //Page.getNumber is zero base
        return new PagingUtil(currentPage.getTotalPages(), currentPage.getNumber() + 1, DEFAULT_PAGE_BAR_SIZE);
    }

    public boolean isPrevButton() {
        return this.prevButton;
    }

    public boolean isNextButton() {
        return this.nextButton;
    }

    public List<Integer> getPagingNumbers() {
        return this.pagingNumbers;
    }

    public int getNumberOfPrevButton() {
        return numberOfPrevButton;
    }

    public int getNumberOfNextButton() {
        return numberOfNextButton;
    }

    @Override
    public String toString() {
        return "PagingUtil{" +
                "prevButton=" + prevButton +
                ", nextButton=" + nextButton +
                ", pagingNumbers=" + pagingNumbers +
                ", NunberOfPrevButton=" + numberOfPrevButton +
                ", NumberOfNextButton=" + numberOfNextButton +
                '}';
    }
}
