package com.codesquad.qna.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @Lob
    private String comment;

    @Column(nullable = false)
    private LocalDateTime createdTime;

    public Answer() { }

    public Answer(User writer, Question question, String comment) {
        this.writer = writer;
        this.question = question;
        this.comment = comment;
        setCreatedTimeNow();
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public void setCreatedTimeNow() {
        setCreatedTime(LocalDateTime.now());
    }

    public String getFormattedCreatedTime() {
        return this.createdTime == null ? "" : this.createdTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public boolean matchUser(User sessionUser) {
        return this.writer.equals(sessionUser);
    }
}
