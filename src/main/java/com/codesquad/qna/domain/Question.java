package com.codesquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private String title;

    private String contents;

    private LocalDateTime createdDate;

    public Question() {
        createdDate = LocalDateTime.now();
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
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

    public String getCreatedDate() {
        String dateTimePattern = "yyyy년 MM월 dd일 HH시 mm분 ss초";

        return createdDate.format(DateTimeFormatter.ofPattern(dateTimePattern));
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
