package com.codessquad.qna;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LocalDateTime {
    private String localDateTime;

    public LocalDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar time = Calendar.getInstance();
        this.localDateTime = dateFormat.format(time.getTime());
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return localDateTime;
    }
}
