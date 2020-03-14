package com.codessquad.qna.web.dto.posts;

import com.codessquad.qna.domain.Posts;
import com.codessquad.qna.domain.Users;
import java.time.LocalDateTime;

public class PostsListResponseDto {
  private Long Id;
  private String title;
  private Users author;
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

  public Users getAuthor() {
    return this.author;
  }

  public LocalDateTime getModifiedDate() {
    return this.modifiedDate;
  }
}