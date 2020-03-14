package com.codessquad.qna.web.dto.answers;

import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Posts;
import com.codessquad.qna.domain.Users;

public class AnswersResponseDto {

  private Long id;
  private String content;
  private Users author;
  private Posts question;

  public AnswersResponseDto(Answers entity) {
    this.id = entity.getId();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
    this.question = entity.getPosts();
  }

  public AnswersResponseDto() {
  }

  public Long getId() {
    return this.id;
  }

  public Posts getQuestion() {
    return question;
  }

  public void setQuestion(Posts question) {
    this.question = question;
  }

  public String getContent() {
    return this.content;
  }

  public Users getAuthor() {
    return this.author;
  }
}