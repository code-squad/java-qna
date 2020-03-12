package com.codesquad.qna.model;

import com.codesquad.qna.util.DateTimeFormatUtils;
import com.codesquad.qna.util.HtmlDocumentUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JsonProperty
    private Question question;

    @ManyToOne
    private User writer;

    @Column(nullable = false)
    @NotEmpty
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createDateTime;

    @Column(nullable = false)
    private boolean deleted;

    public Answer() {}

    public Answer(Question question, User writer, @NotEmpty String contents) {
        this.question = question;
        this.writer = writer;
        this.contents = contents;
        this.createDateTime = LocalDateTime.now();
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public String getWriter() {
        return writer.getUserId();
    }

    public String getContents() {
        return contents;
    }

    public String getContentsWithBr() {
        return HtmlDocumentUtils.getEntertoBrTag(contents);
    }

    public String getCreateDateTimeToString() {
        return DateTimeFormatUtils.getFormattedLocalDateTime(createDateTime);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean matchWriter(User user) {
        return writer.equals(user);
    }

    public boolean checkDeleteCondition(User user) {
        return matchWriter(user) || deleted;
    }
}
