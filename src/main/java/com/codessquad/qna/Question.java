package com.codessquad.qna;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 20)
    private String writer;

    private String title;
    private String contents;
    private String postingTime;

    public Question() {
        this.postingTime = new LocalDateTime().getCurrentTime();
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

    public Long getId() {
        return id;
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
        return "index: " + id + "\nwriter: " + writer + "\ntitle: " + title + "\ncontents: " + contents +
                "\npostingTime: " + postingTime + "\n";
    }
}
