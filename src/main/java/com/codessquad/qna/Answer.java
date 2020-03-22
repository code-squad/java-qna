package com.codessquad.qna;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_question"))
    private Question question;

    @NotEmpty
    private String contents;

    private boolean deleted;
    private LocalDateTime postingTime;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    protected Answer() {}

    protected Answer(User writer, Question question, String contents) {
        this.writer = writer;
        this.question = question;
        this.contents = contents;
        this.deleted = false;
        this.postingTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public User getWriter() {
        return writer;
    }

    public Question getQuestion() {
        return question;
    }

    public String getContents() {
        return contents;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public String getPostingTime() {
        return postingTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public void update(String contents) {
        this.contents = contents;
        this.postingTime = LocalDateTime.now();
    }

    public void delete() {
        this.deleted = true;
    }

    public boolean isDeletable(User QuestionWriter) {
        return QuestionWriter.equals(this.writer);
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public boolean isWriterEquals(User sessionUser) {
        return sessionUser.equals(writer);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
