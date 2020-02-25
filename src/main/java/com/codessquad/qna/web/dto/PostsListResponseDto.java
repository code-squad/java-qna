package com.codessquad.qna.web.dto;

import com.codessquad.qna.domain.Posts;
import java.time.LocalDateTime;

public class PostsListResponseDto {
  private String title;
  private String content;
  private String author;
  private LocalDateTime createdTime;

  public PostsListResponseDto(Posts entity) {
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
    this.createdTime = entity.
  }
}
