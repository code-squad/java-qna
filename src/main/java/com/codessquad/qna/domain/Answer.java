package com.codessquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User writer;

    private String contents;

    private LocalDateTime createdAt;

    private boolean deleted = false;

    public Answer() {
        this.markCreatedTime();
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void update(Answer answer) {
        if (!isValid(answer)) {
            throw new RuntimeException("InvalidUpdateAnswer");
        }
        this.contents = answer.contents;

    }

    private boolean isValid(Answer answer) {
        return answer.contents != null;
    }

    public void delete() {
        this.deleted = true;
    }

    private void markCreatedTime() {
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id=" + id +
                ", question=" + question +
                ", writer=" + writer +
                ", contents='" + contents + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
