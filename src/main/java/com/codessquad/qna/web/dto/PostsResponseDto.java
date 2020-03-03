package com.codessquad.qna.web.dto;

import com.codessquad.qna.domain.Posts;

public class PostsResponseDto {

  private Long id;
  private String title;
  private String content;
  private String author;

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

  public String getAuthor() {
    return this.author;
  }
}