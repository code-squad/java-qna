package com.codessquad.qna.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question extends AbstractEntity{
    @Column(nullable = false)
    @JsonProperty
    private String title;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @OneToMany(mappedBy = "question")
    @OrderBy("id asc")
    @JsonIgnore
    private List<Answer> answers;

    @Lob
    @JsonProperty
    private String contents;
    @JsonProperty
    private boolean deleted;
    @JsonProperty
    private Integer countOfAnswer;

    public Question() {
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.deleted = false;
        this.countOfAnswer = 0;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Integer getCountOfAnswer() {
        return this.countOfAnswer;
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public boolean isDeletable() {
        return isNoAnswers() || isSameBetweenWritersOfAnswers();
    }

    public boolean isNoAnswers() {
        return answers.isEmpty();
    }

    public boolean isSameBetweenWritersOfAnswers() {
        return answers.stream().allMatch(answer -> answer.matchWriter(this.writer));
    }

    public void delete() {
        deleted = true;
        answers.forEach(Answer::delete);
    }

    public void addAnswer() {
        this.countOfAnswer+=1;
    }

    public void deleteAnswer() {
        this.countOfAnswer-=1;
    }
}
