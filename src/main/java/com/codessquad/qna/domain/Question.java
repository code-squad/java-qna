package com.codessquad.qna.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 20)
    private String writer;
    @Column(nullable = false, length = 50)
    private String title;
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

    public String getWriteTime() {
        return writeTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setWriteTime() {
        this.writeTime = LocalDateTime.now();
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
