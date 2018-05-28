package codesquad.domain;

public class PageUtils {

    public static final int COUNT_OF_QUESTIONS_IN_PAGE = 3;
    private static final int MAX_PAGE = 5;

    public static String createButton(int countOfQuestion, int requestPage) {
        StringBuilder page = new StringBuilder();
        int totalPage = calculateTotalPage(countOfQuestion);
        int nowPages = requestPage / MAX_PAGE;
        int startPage = nowPages * MAX_PAGE;
        int lastPage = calculateLastPage(totalPage, nowPages, startPage);

        page.append(createPreviousButton(nowPages, startPage));
        page.append(createPageButton(startPage, lastPage, requestPage));
        page.append(createNextButton(totalPage, nowPages, lastPage));
        return page.toString();
    }

    public static String createPreviousButton(int nowPages, int startPage) { //현재 보여지는 페이지들이 첫번째가 아닐때 버튼 생성
        if (nowPages == 0) {
            return "";
        }
        return "<li><a href=\"/" + (startPage - 1) + "\">«</a></li>";
    }

    public static String createPageButton(int startPage, int lastPage, int requestPage) {
        StringBuilder page = new StringBuilder();
        for (int i = startPage; i <= lastPage; i++) {
            page.append(nowPageHilight(i, requestPage));
        }
        return page.toString();
    }

    public static String createNextButton(int totalPage, int nowPages, int lastPage) {
        if ((totalPage / MAX_PAGE) <= nowPages) {
            return "";
        }
        return "<li><a href=\"/" + (lastPage + 1) + "\">»</a></li>";
    }

    public static int calculateLastPage(int totalPage, int nowPages, int startPage) {
        if (totalPage / MAX_PAGE == nowPages) {
            return totalPage;
        }
        return startPage + (MAX_PAGE - 1);
    }

    public static int calculateTotalPage(int countOfQuestion) {
        if (countOfQuestion % COUNT_OF_QUESTIONS_IN_PAGE > 0) {
            return countOfQuestion / COUNT_OF_QUESTIONS_IN_PAGE;
        }
        return countOfQuestion / COUNT_OF_QUESTIONS_IN_PAGE - 1;
    }

    public static String nowPageHilight(int i, int requestPage) {     //현재 페이지를 굵게 표시한다.
        if (requestPage != i) {
            return "<li><a href=\"/" + i + "\">" + (i + 1) + "</a></li>";
        }
        return "<li><a href=\"/" + i + "\"><strong>" + (i + 1) + "</strong></a></li>";
    }
}
