package com.codesquad.qna.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatUtils {
    public static String getFormattedLocalDateTime(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
