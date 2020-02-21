package com.codessquad.qna;

import java.text.SimpleDateFormat;

public class Question {
    private int id;
    private String date;
    private String writer;
    private String title;
    private String contents;

    public Question() {
        long time = System.currentTimeMillis();
        SimpleDateFormat daytime = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        date = daytime.format(time);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
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

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
