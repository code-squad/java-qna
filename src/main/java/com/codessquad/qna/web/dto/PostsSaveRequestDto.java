package com.codessquad.qna.web.dto;

import com.codessquad.qna.domain.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
  private String title;
  private String author;
  private String content;

  @Builder
  public PostsSaveRequestDto(String title, String author, String content) {
    this.title = title;
    this.author = author;
    this.content = content;
  }

  public Posts toEntity() {
    return Posts.builder()
        .title(title)
        .author(author)
        .content(content)
        .build();
  }
}
