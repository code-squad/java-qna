package com.codessquad.qna.dto;

import com.codessquad.qna.domain.Question;

import java.time.LocalDateTime;

public class QuestionDto {

    private Long id;

    private UserDto writer;

    private String title;

    private String contents;

    private LocalDateTime createdAt;

    private int countOfAnswer;

    public QuestionDto(Question question) {
        this.id = question.getId();
        this.writer = new UserDto(question.getWriter());
        this.title = question.getTitle();
        this.contents = question.getContents();
        this.createdAt = question.getCreated();
        this.countOfAnswer = question.getCountOfAnswer();
    }

    public Long getId() {
        return id;
    }

    public UserDto getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getCountOfAnswer() {
        return countOfAnswer;
    }

}
