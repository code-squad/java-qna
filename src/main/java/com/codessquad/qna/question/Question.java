package com.codessquad.qna.question;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Question {
    private int index;
    private String writer;
    private String title;
    private String contents;
    private String writtenTime;

    public Question(String writer, String title, String contents, String writtenTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.writtenTime = writtenTime;
    }

    public void setIndex(int index) {
        this.index = index;
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

    public String getWrittenTime() {
        return writtenTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writtenTime='" + writtenTime + '\'' +
                '}';
    }
}
