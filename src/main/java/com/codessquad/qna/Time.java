package com.codessquad.qna;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    private String currentTime;

    public Time() {
        this.currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getCurrentTime() {
        return currentTime;
    }

    @Override
    public String toString() {
        return currentTime;
    }
}
