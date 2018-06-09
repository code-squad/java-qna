package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PaginationUtilTest {
    private final int BLOCK_SIZE = 5;
    private final int PAGE_SIZE = 5;

    @Test
    public void getStartPage() {
        int BLOCK_SIZE = 5;
        int currentPage = 23;
        assertThat(PaginationUtil.getBlockStartPage(BLOCK_SIZE, currentPage),is(21));
    }

    @Test
    public void getEndPage() {
        int totalPages = 18;
        int currentPage = 11;
        int pageBlockSize = 5;
        int startPage = PaginationUtil.getBlockStartPage(pageBlockSize, currentPage);
        assertThat(PaginationUtil.getEndPage(pageBlockSize, totalPages, startPage),is(15));
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
        assertThat(PaginationUtil.getBlockNumberCurrentPage(currentPage, BLOCK_SIZE),is(2));
    }
}
