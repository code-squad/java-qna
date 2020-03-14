package com.codessquad.qna.web.dto.answers;

public class AnswersUpdateRequestDto {
  private String title;
  private String content;

  public AnswersUpdateRequestDto(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public AnswersUpdateRequestDto() {
  }

  public static AnswersUpdateRequestDtoBuilder builder() {
    return new AnswersUpdateRequestDtoBuilder();
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public static class AnswersUpdateRequestDtoBuilder {

    private String title;
    private String content;

    AnswersUpdateRequestDtoBuilder() {
    }

    public AnswersUpdateRequestDto.AnswersUpdateRequestDtoBuilder title(String title) {
      this.title = title;
      return this;
    }

    public AnswersUpdateRequestDto.AnswersUpdateRequestDtoBuilder content(String content) {
      this.content = content;
      return this;
    }

    public AnswersUpdateRequestDto build() {
      return new AnswersUpdateRequestDto(title, content);
    }

    public String toString() {
      return "AnswersUpdateRequestDto.AnswersUpdateRequestDtoBuilder(title=" + this.title + ", content="
          + this.content + ")";
    }
  }
}