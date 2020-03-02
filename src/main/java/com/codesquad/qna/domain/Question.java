package com.codesquad.qna.domain;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Question {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @Lob
    @NotEmpty
    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private LocalDateTime createdTime;


    private Integer countOfAnswers = 0;

    public Question() {
        setCreatedTimeNow();
    }

    public Question(User writer, String title, String contents) {
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        setCreatedTimeNow();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (createdTime == null) {
            return "";
        }

        return createdTime.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public void update(Question question) {
        this.title = question.getTitle();
        this.contents = question.getContents();
        setCreatedTimeNow(); //수정된 시간으로 업데이트
    }

    public boolean matchUser(User sessionUser) {
        return sessionUser.getUserId().equals(writer.getUserId());
    }

    public Integer getCountOfAnswers() {
        return countOfAnswers;
    }

    public void addAnswer() {
        this.countOfAnswers++;
    }
}
