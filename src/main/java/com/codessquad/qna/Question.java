package com.codessquad.qna;

import java.time.LocalDate;

public class Question {

    public int index;
    public String writer;
    public String title;
    public String contents;
    public LocalDate localDate;

    public LocalDate getLocalDate() { return localDate; }

    public int getIndex() { return index; }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getWriter() {
        return writer;
    }

    public void setLocalDate(LocalDate localDate) { this.localDate = localDate; }

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

    @Override
    public String toString() {
        return "Question{" +
                "index=" + index +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", localDate=" + localDate +
                '}';
    }
}
