package com.codessquad.qna.dto;

import com.codessquad.qna.domain.Answer;

import java.time.LocalDateTime;

public class AnswerDto {
    private final Long id;

    private final QuestionDto question;

    private final UserDto writer;

    private final String contents;

    private final LocalDateTime createdAt;

    public AnswerDto(Answer answer) {
        this.id = answer.getId();
        this.question = new QuestionDto(answer.getQuestion());
        this.writer = new UserDto(answer.getWriter());
        this.contents = answer.getContents();
        this.createdAt = answer.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public QuestionDto getQuestion() {
        return question;
    }

    public UserDto getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
