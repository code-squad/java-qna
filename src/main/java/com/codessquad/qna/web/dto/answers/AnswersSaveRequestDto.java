package com.codessquad.qna.web.dto.answers;

import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Users;
import javax.servlet.http.HttpSession;

public class AnswersSaveRequestDto {
  private String title;
  private String content;
  private Long postId;
  private Users author;

  public AnswersSaveRequestDto(String title, String content, Long postId, Users author) {
    this.title = title;
    this.content = content;
    this.postId = postId;
    this.author = author;
  }

  public AnswersSaveRequestDto() {
  }

  public static AnswersSaveRequestDtoBuilder builder() {
    return new AnswersSaveRequestDtoBuilder();
  }

  public Answers toEntity() {
    return Answers.builder()
        .title(title)
        .author()
        .content(content)
        .question(postId)
        .build();
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public static class AnswersSaveRequestDtoBuilder {

    private String title;
    private Users author;
    private String content;
    private Long postId;

    AnswersSaveRequestDtoBuilder() {
    }

    public AnswersSaveRequestDto.AnswersSaveRequestDtoBuilder title(String title) {
      this.title = title;
      return this;
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
      return new AnswersSaveRequestDto(title, content, postId, author);
    }

    @Override
    public String toString() {
      return "AnswersSaveRequestDtoBuilder{" +
          "title='" + title + '\'' +
          ", author=" + author +
          ", content='" + content + '\'' +
          ", postId=" + postId +
          '}';
    }
  }
}