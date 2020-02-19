package com.codessquad.qna;

import jdk.vm.ci.meta.Local;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private int index;
    private LocalDateTime createdTime;
    private static String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public void setIndex(int index) {
        this.index = index;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public int getIndex() {
        return index;
    }

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public String getFormattedCreatedTime() {
        return createdTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }


}
