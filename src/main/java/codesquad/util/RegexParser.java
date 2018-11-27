package codesquad.util;

public class RegexParser {
    public static String newLineMaker(String contents) {
        return contents.replaceAll("(\r\n|\n)", "<br />");
    }
}
