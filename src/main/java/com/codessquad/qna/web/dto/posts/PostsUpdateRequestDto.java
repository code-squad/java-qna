package com.codessquad.qna.web.dto.posts;

public class PostsUpdateRequestDto {
  private String title;
  private String content;

  public PostsUpdateRequestDto(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public PostsUpdateRequestDto() {
  }

  public static PostsUpdateRequestDtoBuilder builder() {
    return new PostsUpdateRequestDtoBuilder();
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public static class PostsUpdateRequestDtoBuilder {

    private String title;
    private String content;

    PostsUpdateRequestDtoBuilder() {
    }

    public PostsUpdateRequestDto.PostsUpdateRequestDtoBuilder title(String title) {
      this.title = title;
      return this;
    }

    public PostsUpdateRequestDto.PostsUpdateRequestDtoBuilder content(String content) {
      this.content = content;
      return this;
    }

    public PostsUpdateRequestDto build() {
      return new PostsUpdateRequestDto(title, content);
    }

    public String toString() {
      return "PostsUpdateRequestDto.PostsUpdateRequestDtoBuilder(title=" + this.title + ", content="
          + this.content + ")";
    }
  }
}