package com.codessquad.qna.web.dto.answers;

import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Users;
import java.time.LocalDateTime;

public class AnswersListResponseDto {
  private Long Id;
  private String title;
  private Users author;
  private LocalDateTime modifiedDate;

  public AnswersListResponseDto(Answers entity) {
    this.Id = entity.getId();
    this.title = entity.getTitle();
    this.author = entity.getAuthor();
    this.modifiedDate= entity.getModifiedDate();
  }

  public Long getId() {
    return this.Id;
  }

  public String getTitle() {
    return this.title;
  }

  public Users getAuthor() {
    return this.author;
  }

  public LocalDateTime getModifiedDate() {
    return this.modifiedDate;
  }

}