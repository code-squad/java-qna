package com.codessquad.qna;

public class Question {
    private String writer;
    private String title;
    private String contents;
    private String postingTime;

    public Question() {
        LocalDateTime localDateTime = new LocalDateTime();
        this.postingTime = localDateTime.getLocalDateTime();
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

    public String getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getPostingTime() {
        return postingTime;
    }

    @Override
    public String toString() {
        return "writer : " + writer + "\ntitle : " + title + "\ncontents : " + contents + "\npostingTime : " + postingTime;
    }
}
