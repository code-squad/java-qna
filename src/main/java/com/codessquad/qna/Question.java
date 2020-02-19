package com.codessquad.qna;

import java.time.LocalDateTime;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private int index;
    private String dateTime;

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

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

    public String getDateTime() {
        return dateTime;
    }

}
