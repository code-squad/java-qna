package com.codessquad.qna.domain;

import javax.persistence.*;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @Column(nullable=false)
    private String title;
    private String contents;

    public Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
    }

    public Long getId() {
        return id;
    }
    public User getWriter() { return writer; }
    public String getTitle() { return title; }
    public String getContents() { return contents; }

    public void setId(Long id) { this.id = id; }
    public void setWriter(User writer) { this.writer = writer; }
    public void setTitle(String title) { this.title = title; }
    public void setContents(String contents) { this.contents = contents; }

    public void update (Question newQuestion) {
        this.title = newQuestion.title;
        this.contents = newQuestion.contents;
    }

    @Override
    public String toString() {
        return "Question {" +
                "id=" + id +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
