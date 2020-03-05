package com.codessquad.qna.web.dto.answers;

import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Users;

public class AnswersResponseDto {

  private Long id;
  private String title;
  private String content;
  private Users author;

  public AnswersResponseDto(Answers entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
  }

  public AnswersResponseDto() {
  }

  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public Users getAuthor() {
    return this.author;
  }
}