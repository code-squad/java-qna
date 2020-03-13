package com.codessquad.qna.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 20)
    private String writer;

    @NotBlank
    @Column(length = 50)
    private String title;

    @NotBlank
    @Type(type = "text")
    private String contents;

    private LocalDateTime writeTime;

    public Long getId() {
        return id;
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

    public void setWriteTime(LocalDateTime writeTime) {
        this.writeTime = writeTime;
    }

    public String getWriteTime() {
        return writeTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setWriteTimeNow() {
        setWriteTime(LocalDateTime.now());
    }

    public void update(Question updatedQuestion) {
        title = updatedQuestion.title;
        contents = updatedQuestion.contents;
    }
    
    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writeTime=" + writeTime +
                '}';
    }
}
