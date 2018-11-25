package codesquad.domain.util;

public class UrlFormat {
    public static String urlConverter(String url, Long no) {
        return String.format("redirect:%s%d", url, Long.valueOf(no));
    }
}
