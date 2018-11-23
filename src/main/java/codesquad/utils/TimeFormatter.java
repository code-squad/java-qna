package codesquad.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {
    public static final String COMMON_FORMAT = "yyyy-MM-dd HH:mm";

    public static String commonFormat(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern(COMMON_FORMAT));
    }
}
