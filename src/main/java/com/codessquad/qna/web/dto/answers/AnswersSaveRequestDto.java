package com.codessquad.qna.web.dto.answers;

import com.codessquad.qna.domain.Answers;
import com.codessquad.qna.domain.Users;
import javax.servlet.http.HttpSession;

public class AnswersSaveRequestDto {
  private String title;
  private String content;

  public AnswersSaveRequestDto(String title, String content) {
    this.title = title;
    this.content = content;
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

    public AnswersSaveRequestDto build() {
      return new AnswersSaveRequestDto(title, content);
    }

    public String toString() {
      return "AnswersSaveRequestDto.AnswersSaveRequestDtoBuilder(title=" + this.title + ", author="
          + this.author + ", content=" + this.content + ")";
    }
  }
}