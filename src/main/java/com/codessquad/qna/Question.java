package com.codessquad.qna;

public class Question {
    private String writer;
    private String title;
    private String contents;

    public void setName(String name) {
        this.writer = name;
    }

    public void setSubtitle(String subtitle) {
        this.title = subtitle;
    }

    public void setContent(String content) {
        this.contents = content;
    }

    public String getName() {
        return writer;
    }

    public String getSubtitle() {
        return title;
    }

    public String getContent() {
        return contents;
    }

    @Override
    public String toString() {
        return "name : " + writer + "\n subtitle : " + title + "\n content : " + contents;
    }
}
