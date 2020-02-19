package com.codessquad.qna;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private String now;

    public Question() {
        Date today = new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm");

        now = date.format(today) + " " + time.format(today);
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
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", now ='" + now + '\'' +
                '}';
    }
}
