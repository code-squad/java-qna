package com.codesquad.qna.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long questionId;
    @ManyToOne
    private User writer;
    @Column(nullable = false)
    @NotEmpty
    private String title;
    private String contents;
    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    public Question() {
    }

    public Question(User writer, String title, String contents, LocalDateTime createdDateTime) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDateTime = createdDateTime;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public String getWriter() {
        return writer.getName();
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public String getCreatedDateTimetoString() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdDateTime);
    }

    public boolean matchWriter(User sessionedUser) {
        return this.writer.equals(sessionedUser.getUserId());
    }

    public void update(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", writer='" + writer + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", createdDateTime=" + getCreatedDateTimetoString() +
                '}';
    }
}
