package codesquad.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PaginationUtilTest {
    private final int BLOCK_SIZE = 5;
    private final int PAGE_SIZE = 5;

    @Test
    public void getStartPage() {
        int BLOCK_SIZE = 5;
        int currentPage = 23;
        assertThat(PaginationUtil.getBlockStartPage(BLOCK_SIZE, currentPage), is(21));
    }

    @Test
    public void getEndPage() {
        int totalPages = 18;
        int currentPage = 11;
        int pageBlockSize = 5;
        int startPage = PaginationUtil.getBlockStartPage(pageBlockSize, currentPage);
        assertThat(PaginationUtil.getBlockEndPage(pageBlockSize, totalPages, startPage), is(15));
    }

    @Test
    public void isFirstBlock() {
        int pageBlockSize = 5;
        int currentPage = 4;
        int startPage = PaginationUtil.getBlockStartPage(pageBlockSize, currentPage);
        System.out.println(startPage);
        assertThat(PaginationUtil.isFirstBlock(startPage), is(true));
    }

    @Test
    public void getBlockNumberCurrentPage() {
        int currentPage = 17;
        assertThat(PaginationUtil.getBlockNumberCurrentPage(currentPage, BLOCK_SIZE), is(4));
    }

    @Test
    public void getCurrentBlockPageList() {
        int currentPage = 7;
        int totalPages = 27;
        List<Integer> pageList = Arrays.asList(6, 10);
        List<Integer> newPageList = PaginationUtil.getCurrentBlockPageList(currentPage, totalPages);
        assertThat(pageList.equals(newPageList), is(true));
    }

    @Test
    public void getCurrentPageBlockHTML() {
        int currentPage = 13;
        int totalPages = 27;
        int startNo = 11;
        int endNo = 15;
        StringBuilder sb = new StringBuilder();
        for (int i = startNo; i <= endNo; i++) {
            sb.append("<li><a href=\"/?page=");
            sb.append(i);
            sb.append("\">");
            sb.append(i);
            sb.append("</a></li>");
        }
        String returnedPageBlockHTML = PaginationUtil.getCurrentPageBlockHTML(currentPage, totalPages);
        String pageBlockHTML = sb.toString();
        System.out.println(returnedPageBlockHTML);
        System.out.println(pageBlockHTML);
        assertThat(pageBlockHTML.equals(returnedPageBlockHTML), is(true));
    }
}
