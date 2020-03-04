package com.codessquad.qna.common.constants;

import java.time.format.DateTimeFormatter;

public class FormatConstants {

    public static final DateTimeFormatter POST_DATA_DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");

    private FormatConstants() {}
}
