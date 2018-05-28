package codesquad.domain;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

public class PageUtilsTest {

    @Test
    public void createPreviousButtonTest() {
        int nowPages = 1;
        int startPage = 2;
        assertThat(PageUtils.createPreviousButton(nowPages, startPage), is("<li><a href=\"/1\">«</a></li>"));
        assertThat(PageUtils.createPreviousButton(0, startPage), is(""));
    }

    @Test
    public void createPageButtonTest() {
        int startPage = 0;
        int lastPage = 4;
        int requestPage = 4;
        StringBuilder test = new StringBuilder();

        test.append("<li><a href=\"/0\">1</a></li>");
        test.append("<li><a href=\"/1\">2</a></li>");
        test.append("<li><a href=\"/2\">3</a></li>");
        test.append("<li><a href=\"/3\">4</a></li>");
        test.append("<li><a href=\"/4\"><strong>5</strong></a></li>");
        assertThat(PageUtils.createPageButton(startPage, lastPage, requestPage), is(test.toString()));
    }

    @Test
    public void createNextButtonTest() {
        int totalPage = 7;
        int nowPages = 0;
        int lastPage = 4;
        String test = "<li><a href=\"/5\">»</a></li>";
        assertThat(PageUtils.createNextButton(totalPage, nowPages, lastPage), is(test));
    }

    @Test
    public void createNextButton2Test() {
        int totalPage = 4;
        int nowPages = 0;
        int lastPage = 4;
        String test = "";
        assertThat(PageUtils.createNextButton(totalPage, nowPages, lastPage), is(test));
    }

    @Test
    public void calculateLastPageTest() {
        int totalPage = 11;
        int nowPages = 1;
        int startPage = 5;
        assertThat(PageUtils.calculateLastPage(totalPage, nowPages, startPage), is(9));
    }

    @Test
    public void calculateLastPage2Test() {
        int totalPage = 11;
        int nowPages = 2;
        int startPage = 5;
        assertThat(PageUtils.calculateLastPage(totalPage, nowPages, startPage), is(11));
    }

    @Test
    public void nowPageHilightTest() {
        assertThat(PageUtils.nowPageHilight(2, 2), is("<li><a href=\"/2\"><strong>3</strong></a></li>"));
    }
}