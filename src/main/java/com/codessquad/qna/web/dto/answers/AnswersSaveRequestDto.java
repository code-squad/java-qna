package com.codessquad.qna.web.dto.answers;

import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Users;
import javax.servlet.http.HttpSession;

public class AnswersSaveRequestDto {

  private Users author;
  private String content;
  private Long postId;

  public AnswersSaveRequestDto(Users author, String content, Long postId) {
    this.author = author;
    this.content = content;
    this.postId = postId;
  }

  public AnswersSaveRequestDto() {
  }

  public static AnswersSaveRequestDtoBuilder builder() {
    return new AnswersSaveRequestDtoBuilder();
  }

  public Answers toEntity() {
    return Answers.builder()
        .author()
        .question(postId)
        .content(content)
        .build();
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  public static class AnswersSaveRequestDtoBuilder {

    private Users author;
    private String content;
    private Long postId;

    AnswersSaveRequestDtoBuilder() {
    }

    public AnswersSaveRequestDto.AnswersSaveRequestDtoBuilder author(HttpSession httpSession) {
      this.author = (Users) httpSession.getAttribute("sessionUser");
      return this;
    }

    public AnswersSaveRequestDto.AnswersSaveRequestDtoBuilder content(String content) {
      this.content = content;
      return this;
    }

    public AnswersSaveRequestDto.AnswersSaveRequestDtoBuilder postId(Long postId) {
      this.postId = postId;
      return this;
    }

    public AnswersSaveRequestDto build() {
      return new AnswersSaveRequestDto(author, content, postId);
    }

    @Override
    public String toString() {
      return "AnswersSaveRequestDtoBuilder{" +
          "author=" + author +
          ", content='" + content + '\'' +
          ", postId=" + postId +
          '}';
    }
  }
}