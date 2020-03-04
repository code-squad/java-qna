package com.codesquad.qna.web;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private String date;
    private long id;

    public Question() {
        setDate();
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

    public void setDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();

        this.date = dateFormat.format(date);
    }

    public void setId(long id) {
        this.id = id;
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

    public String getDate() {
        return date;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Writer : " + writer + "\n" +
                "Title : " + title + "\n" +
                "Contents : " + contents + "\n" +
                "Date : " + date + "\n" +
                "Id : " + id;
    }
}
