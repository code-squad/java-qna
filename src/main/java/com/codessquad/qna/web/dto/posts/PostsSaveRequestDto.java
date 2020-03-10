package com.codessquad.qna.web.dto.posts;

import com.codessquad.qna.domain.Posts;
import com.codessquad.qna.domain.Users;
import javax.servlet.http.HttpSession;

public class PostsSaveRequestDto {

  private String title;
  private Users author;
  private String content;

  public PostsSaveRequestDto(String title, Users author, String content) {
    this.title = title;
    this.author = author;
    this.content = content;
  }

  public PostsSaveRequestDto() {
  }

  public static PostsSaveRequestDtoBuilder builder() {
    return new PostsSaveRequestDtoBuilder();
  }

  public Posts toEntity() {
    return Posts.builder()
        .title(title)
        .author()
        .content(content)
        .build();
  }

  public String getTitle() {
    return this.title;
  }

  public Users getAuthor() {
    return this.author;
  }

  public String getContent() {
    return this.content;
  }

  public static class PostsSaveRequestDtoBuilder {

    private String title;
    private Users author;
    private String content;

    PostsSaveRequestDtoBuilder() {
    }

    public PostsSaveRequestDto.PostsSaveRequestDtoBuilder title(String title) {
      this.title = title;
      return this;
    }

    public PostsSaveRequestDto.PostsSaveRequestDtoBuilder author(HttpSession httpSession) {
      this.author = (Users) httpSession.getAttribute("sessionUser");
      return this;
    }

    public PostsSaveRequestDto.PostsSaveRequestDtoBuilder content(String content) {
      this.content = content;
      return this;
    }

    public PostsSaveRequestDto build() {
      return new PostsSaveRequestDto(title, author, content);
    }

    public String toString() {
      return "PostsSaveRequestDto.PostsSaveRequestDtoBuilder(title=" + this.title + ", author="
          + this.author + ", content=" + this.content + ")";
    }
  }
}
