package codesquad.domain.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormat {
    public static String getCurrentTime() {
        return getTimeFormat(LocalDateTime.now());
    }

    public static String getTimeFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
