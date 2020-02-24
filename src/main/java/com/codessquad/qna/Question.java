package com.codessquad.qna;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime currentTime;

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(LocalDateTime currentTime) {
        this.currentTime = currentTime;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getFormattedTime() {
        return currentTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
