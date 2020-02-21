package com.codesquad.qna;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private int index;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime createdTime;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getFormattedCreatedTime() {
        return createdTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }


}
