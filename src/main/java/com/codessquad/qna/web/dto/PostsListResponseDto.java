package com.codessquad.qna.web.dto;

import com.codessquad.qna.domain.Posts;
import java.time.LocalDateTime;

public class PostsListResponseDto {
  private Long Id;
  private String title;
  private String author;
  private LocalDateTime modifiedDate;

  public PostsListResponseDto(Posts entity) {
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

  public String getAuthor() {
    return this.author;
  }

  public LocalDateTime getModifiedDate() {
    return this.modifiedDate;
  }
}