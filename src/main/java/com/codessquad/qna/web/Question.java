package com.codessquad.qna.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static int lastId = 0;

    private int id;
    private String writer;
    private String title;
    private String contents;
    private LocalDateTime localDateTime;

    {
        id = ++lastId;
        localDateTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getDateTime() {
        return localDateTime.format(formatter);
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer=" + writer +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
