package codesquad.domain;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PaginationUtilTest {
    @Test
    public void getStartPage() {
        int currentPage = 23;
        assertThat(PaginationUtil.getBlockStartPage(currentPage), is(21));
    }

    @Test
    public void getEndPage() {
        int totalPages = 18;
        int currentPage = 11;
        int startPage = PaginationUtil.getBlockStartPage(currentPage);
        assertThat(PaginationUtil.getBlockEndPage(totalPages, startPage), is(15));
    }

    @Test
    public void isFirstBlock() {
        int currentPage = 4;
        int blockNo = PaginationUtil.getCurrentPageBlockNumber(currentPage);
        assertThat(PaginationUtil.isFirstBlock(blockNo), is(true));
    }

    @Test
    public void isLastBlock() {
        // lastBlockNo is 3
        int currentPage = 12;
        int totalPages = 14;
        int currentBlockNo = PaginationUtil.getCurrentPageBlockNumber(currentPage);
        assertThat(PaginationUtil.isLastBlock(currentBlockNo, totalPages), is(true));
    }

    @Test
    public void getLastBlockNo() {
        int lastBlockNo = 4;
        int totalPages = 17;
        assertThat(lastBlockNo == PaginationUtil.getLastBlockNo(totalPages), is(true));
    }

    @Test
    public void getBlockNumberCurrentPage() {
        int currentPage = 17;
        assertThat(PaginationUtil.getCurrentPageBlockNumber(currentPage), is(4));
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

        sb.append(PaginationUtil.getBlockLeft(startNo, currentPage));
        sb.append(PaginationUtil.getBlockBody(startNo, endNo, currentPage));
        sb.append(PaginationUtil.getBlockRight(endNo, currentPage, totalPages));

        String returnedPageBlockHTML = PaginationUtil.getCurrentPageBlockHTML(currentPage, totalPages);
        String pageBlockHTML = sb.toString();
        assertThat(pageBlockHTML.equals(returnedPageBlockHTML), is(true));
    }

    @Test
    public void getBlockHTMLRemovedLeftArrow() {
        int currentPage = 2;
        int totalPages = 6;
        int startNo = 1;
        int endNo = 5;

        StringBuilder sb = new StringBuilder();
        sb.append(PaginationUtil.getBlockLeft(startNo, currentPage));
        sb.append(PaginationUtil.getBlockBody(startNo, endNo, currentPage));
        sb.append(PaginationUtil.getBlockRight(endNo, currentPage, totalPages));

        String returnedPageBlockHTML = PaginationUtil.getCurrentPageBlockHTML(currentPage, totalPages);
        String pageBlockHTML = sb.toString();

        assertThat(pageBlockHTML.equals(returnedPageBlockHTML), is(true));
    }

    @Test
    public void getBlockHTMLRemovedRightArrow() {
        int currentPage = 6;
        int totalPages = 8;
        int startNo = 6;
        int endNo = 8;

        StringBuilder sb = new StringBuilder();
        sb.append(PaginationUtil.getBlockLeft(startNo, currentPage));
        sb.append(PaginationUtil.getBlockBody(startNo, endNo, currentPage));
        sb.append(PaginationUtil.getBlockRight(endNo, currentPage, totalPages));

        String returnedPageBlockHTML = PaginationUtil.getCurrentPageBlockHTML(currentPage, totalPages);
        String pageBlockHTML = sb.toString();

        assertThat(pageBlockHTML.equals(returnedPageBlockHTML), is(true));
    }

    @Test
    public void getBlockBody() {
        int startNo = 1;
        int endNo = 5;
        int currentPage = 3;
        String blockBody = "<li><a href=\"/?page=0\">1</a></li><li><a href=\"/?page=1\">2</a></li><li class=\"active\"><a href=\"/?page=2\">3</a></li><li><a href=\"/?page=3\">4</a></li><li><a href=\"/?page=4\">5</a></li>";
        String newBlockBody = PaginationUtil.getBlockBody(startNo, endNo, currentPage);
        assertThat(blockBody.equals(newBlockBody), is(true));
    }

    @Test
    public void getLeftArrowHTMLCode() {
        int currentPage = 6;
        int startNo = 6;
        String leftArrowHTMLCode = "<li><a href=\"/?page=4\">«</a></li>";
        String newLeftArrowHTMLCode = PaginationUtil.getBlockLeft(startNo, currentPage);
        assertThat(leftArrowHTMLCode.equals(newLeftArrowHTMLCode),is(true));
    }

    @Test
    public void getRightArrowHTMLCode() {
        int endNo = 5;
        int currentPage = 4;
        int totalPages = 7;
        String rightArrowHTMLCode = "<li><a href=\"/?page=5\">»</a></li>";
        String newRightArrowHTMLCode = PaginationUtil.getBlockRight(endNo, currentPage, totalPages);
        assertThat(rightArrowHTMLCode.equals(newRightArrowHTMLCode),is(true));
    }
}
