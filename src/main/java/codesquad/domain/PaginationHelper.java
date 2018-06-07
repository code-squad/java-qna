package codesquad.domain;

import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsHelper;

@HandlebarsHelper
public class PaginationHelper {
//    private int totalPages;
//    private int currentPage;

    public String totalPage(int totalPages) {
        return String.valueOf(totalPages);
    }

    public String pageGroupNumber(int totalPages, int pageNumber) {
        if (totalPages == 0) {
            return "0";
        }
        return String.valueOf(totalPages/pageNumber);
    }
}
