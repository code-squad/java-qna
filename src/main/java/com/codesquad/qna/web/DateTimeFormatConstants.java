package com.codesquad.qna.web;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatConstants {
    public static final DateTimeFormatter BOARD_DATA_DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private DateTimeFormatConstants() {}
}
