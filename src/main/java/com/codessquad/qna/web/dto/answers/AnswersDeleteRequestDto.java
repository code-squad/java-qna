package com.codessquad.qna.web.dto.answers;

public class AnswersDeleteRequestDto {
  private boolean deleteStatus;

  public AnswersDeleteRequestDto(boolean deleteStatus) {
    this.deleteStatus = deleteStatus;
  }

  public AnswersDeleteRequestDto(){
  }

  public static AnswersDeleteRequestDtoBuilder builder() {
    return new AnswersDeleteRequestDtoBuilder();
  }

  public boolean deleteStatusQuo() {
    return deleteStatus;
  }

  public void setDeleteStatus(boolean deleteStatus) {
    this.deleteStatus = deleteStatus;
  }

  public static class AnswersDeleteRequestDtoBuilder {
    private boolean deleteStatus;

    AnswersDeleteRequestDtoBuilder(){
    }

    public AnswersDeleteRequestDto.AnswersDeleteRequestDtoBuilder deleteAnswer(boolean deleteStatus) {
      this.deleteStatus = deleteStatus;
      return this;
    }

    public AnswersDeleteRequestDto build() {
      return new AnswersDeleteRequestDto(deleteStatus);
    }
  }
}
