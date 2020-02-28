package com.codessquad.qna;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public Question() {
        this.setCreated();
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return createdAt;
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

    public void setCreated() {
        this.createdAt = LocalDateTime.now();
    }

    public void update(Question question) {
        this.contents = question.contents;
        this.title = question.title;
    }

    @Override
    public String toString() {
        return "Question{" +
                "writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", id=" + id +
                '}';
    }
}
