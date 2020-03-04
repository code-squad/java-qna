package com.codessquad.qna.error;

import com.codessquad.qna.domain.Answer;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

  private boolean valid;

  private String errorMessage;

  @JsonProperty
  private Answer answer;

  private Result(boolean valid, String errorMessage) {
    this.valid = valid;
    this.errorMessage = errorMessage;
  }

  private Result(boolean valid, Answer answer, String errorMessage) {
    this.valid = valid;
    this.answer = answer;
    this.errorMessage = errorMessage;
  }


  public static Result ok() {
    return new Result(true, null);
  }

  public static Result ok(Answer answer) {
    return new Result(true, answer,null);
  }


  public static Result fail(String errorMessage) {
    return new Result(false, errorMessage);
  }

  public boolean isValid() {
    return valid;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}

