package codesquad.domain;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PaginationUtilTest {
    @Test
    public void getStartPage() {
        int totalPages = 15;
        int currentPage = 6;
        assertThat(PaginationUtil.getStartpage(totalPages, currentPage),is(6));
    }

    @Test
    public void getEndPage() {
        int totalPages = 15;
        int currentPage = 11;
        int pageSize = 5;
        int startPage = PaginationUtil.getStartpage(totalPages, currentPage);
        assertThat(PaginationUtil.getEndPage(pageSize, totalPages, startPage),is(15));
    }
}
