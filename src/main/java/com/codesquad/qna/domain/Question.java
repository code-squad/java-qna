package com.codesquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;
    private String writer;
    private String title;
    private String contents;

    @Column
    private LocalDateTime createdDate;

    public Question() {
        createdDate = LocalDateTime.now();
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

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public Long getId() {
        return id;
    }

    public void update(Question updatedQuestion) {
        title = updatedQuestion.getTitle();
        contents = updatedQuestion.getContents();
    }

    public boolean isSameWriter(User sessionedUser) {
        return writer.equals(sessionedUser.getUserName());
    }

    @Override
    public String toString() {
        return "Writer : " + writer + "\n" +
                "Title : " + title + "\n" +
                "Contents : " + contents + "\n" +
                "Date : " + getCreatedDate() + "\n" +
                "Id : " + id;
    }
}
