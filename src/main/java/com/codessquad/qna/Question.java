package com.codessquad.qna;

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

    @Column(nullable = false)
    private String writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime created;

    public Question() {
        this.created = date();
    }

    public Long getId() {
        return id;
    }

    public static LocalDateTime date() {
        return LocalDateTime.now();
    }

    public LocalDateTime getCreated() {
        return created;
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

    public void setCreated() {
        this.created = date();
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", created='" + created + '\'' +
                ", id=" + id +
                '}';
    }
}
