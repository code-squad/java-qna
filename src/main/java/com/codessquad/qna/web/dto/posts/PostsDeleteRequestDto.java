package com.codessquad.qna.web.dto.posts;

public class PostsDeleteRequestDto {

  private boolean deleteStatus;

  public PostsDeleteRequestDto(boolean deleteStatus) {
    this.deleteStatus = deleteStatus;
  }

  public PostsDeleteRequestDto() {
  }

  public static PostsDeleteRequestDtoBuilder builder() {
    return new PostsDeleteRequestDtoBuilder();
  }

  public boolean deleteStatusQuo() {
    return deleteStatus;
  }

  public void setDeleteStatus(boolean deleteStatus) {
    this.deleteStatus = deleteStatus;
  }

  public static class PostsDeleteRequestDtoBuilder {

    private boolean deleteStatus;

    PostsDeleteRequestDtoBuilder() {
    }

    public PostsDeleteRequestDto.PostsDeleteRequestDtoBuilder deletePost(boolean deleteStatus) {
      this.deleteStatus = deleteStatus;
      return this;
    }

    public PostsDeleteRequestDto build() {
      return new PostsDeleteRequestDto(deleteStatus);
    }
  }
}
