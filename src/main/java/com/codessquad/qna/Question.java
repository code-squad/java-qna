package com.codessquad.qna;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @NotEmpty
    @JsonProperty
    private String title;

    @NotEmpty
    @JsonProperty
    private String contents;

    @JsonProperty
    private boolean deleted;
    private LocalDateTime postingTime;
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

    @OneToMany(mappedBy = "question")
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Answer> answers;

    public Question() {}

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.deleted = false;
        this.postingTime = LocalDateTime.now();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getId() { return id; }

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public LocalDateTime getPostingTime() {
        return postingTime;
    }

    public String getFormattedPostingTime() {
        return postingTime.format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public boolean isWriterEquals(User sessionUser) {
        return sessionUser.equals(writer);
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public void delete() throws IllegalAccessException {
        for (Answer answer : answers) {
            if (!answer.isDeletable(this.writer)) {
                throw new IllegalAccessException("/error/forbidden");
            }
            answer.delete();
        }
        this.deleted = true;
    }

    public int getAnswersCount() {
        int count = 0;
        for (Answer answer : answers) {
            if (!answer.isDeleted()) count++;
        }
        return count;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
