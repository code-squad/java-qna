package com.codesquad.qna.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
public class Answer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Question question;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    @NotEmpty
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createDateTime;

    public Answer() {}

    public Answer(Question question, User user, @NotEmpty String contents) {
        this.question = question;
        this.user = user;
        this.contents = contents;
        this.createDateTime = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Question getQuestion() {
        return question;
    }

    public User getUser() {
        return user;
    }

    public String getContents() {
        return contents;
    }

    public String getCreateDateTimeToString() {
        return DateTimeFormatUtils.getFormattedLocalDateTime(this.createDateTime);
    }

    public boolean matchWriter(User user) {
        return this.user.equals(user);
    }
}
