package com.codessquad.qna;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LocalDateTime {
    private String currentTime;

    public LocalDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar time = Calendar.getInstance();
        this.currentTime = dateFormat.format(time.getTime());
    }

    public String getCurrentTime() {
        return currentTime;
    }

    @Override
    public String toString() {
        return currentTime;
    }
}
