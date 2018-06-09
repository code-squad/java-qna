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

        if (!PaginationUtil.isFirstBlock(currentPage)) {
            sb.append("<li><a href=\"/?page=");
            sb.append(startNo - 2);
            sb.append("\">«</a></li>");
        }
        for (int i = startNo; i <= endNo; i++) {
            sb.append("<li><a href=\"/?page=");
            // pageable에서는 0페이지부터 시작하므로 i-1 해줘서 페이지 넘버를 보정한다.
            sb.append(i - 1);
            sb.append("\">");
            sb.append(i);
            sb.append("</a></li>");
        }
        if (!PaginationUtil.isLastBlock(PaginationUtil.getCurrentPageBlockNumber(currentPage), totalPages)) {
            sb.append("<li><a href=\"/?page=");
            sb.append(endNo);
            sb.append("\">»</a></li>");
        }

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
        if (!PaginationUtil.isFirstBlock(currentPage)) {
            sb.append("<li><a href=\"/?page=");
            sb.append(startNo - 2);
            sb.append("\">«</a></li>");
        }
        for (int i = startNo; i <= endNo; i++) {
            sb.append("<li><a href=\"/?page=");
            // pageable에서는 0페이지부터 시작하므로 i-1 해줘서 페이지 넘버를 보정한다.
            sb.append(i - 1);
            sb.append("\">");
            sb.append(i);
            sb.append("</a></li>");
        }
        if (!PaginationUtil.isLastBlock(PaginationUtil.getCurrentPageBlockNumber(currentPage), totalPages)) {
            sb.append("<li><a href=\"/?page=");
            sb.append(endNo);
            sb.append("\">»</a></li>");
        }

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
        if (!PaginationUtil.isFirstBlock(currentPage)) {
            sb.append("<li><a href=\"/?page=");
            sb.append(startNo - 2);
            sb.append("\">«</a></li>");
        }
        for (int i = startNo; i <= endNo; i++) {
            sb.append("<li><a href=\"/?page=");
            // pageable에서는 0페이지부터 시작하므로 i-1 해줘서 페이지 넘버를 보정한다.
            sb.append(i - 1);
            sb.append("\">");
            sb.append(i);
            sb.append("</a></li>");
        }
        if (!PaginationUtil.isLastBlock(PaginationUtil.getCurrentPageBlockNumber(currentPage), totalPages)) {
            sb.append("<li><a href=\"/?page=");
            sb.append(endNo);
            sb.append("\">»</a></li>");
        }

        String returnedPageBlockHTML = PaginationUtil.getCurrentPageBlockHTML(currentPage, totalPages);
        String pageBlockHTML = sb.toString();

        assertThat(pageBlockHTML.equals(returnedPageBlockHTML), is(true));
    }

    @Test
    public void getLeftArrowHTMLCode() {
        int currentPage = 6;
        int startNo = 6;
        String leftArrowHTMLCode = "<li><a href=\"/?page=4\">«</a></li>";
        String newLeftArrowHTMLCode = PaginationUtil.getLeftArrowHTMLCode(startNo, currentPage);
        assertThat(leftArrowHTMLCode.equals(newLeftArrowHTMLCode),is(true));
    }
}
