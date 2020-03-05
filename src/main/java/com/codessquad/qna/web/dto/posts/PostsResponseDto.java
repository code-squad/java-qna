package com.codessquad.qna.web.dto.posts;

import com.codessquad.qna.domain.Posts;
import com.codessquad.qna.domain.Users;

public class PostsResponseDto {

  private Long id;
  private String title;
  private String content;
  private Users author;

  public PostsResponseDto(Posts entity) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.content = entity.getContent();
    this.author = entity.getAuthor();
  }

  public PostsResponseDto() {
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