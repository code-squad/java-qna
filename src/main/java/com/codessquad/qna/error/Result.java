package com.codessquad.qna.error;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {

  private boolean valid;

  private String errorMessage;

  @JsonProperty
  private Answer answer;

  @JsonProperty
  private Question question;

  private Result(boolean valid, String errorMessage) {
    this.valid = valid;
    this.errorMessage = errorMessage;
  }

  private Result(boolean valid, Answer answer) {
    this.valid = valid;
    this.answer = answer;
  }

  private Result(boolean valid, Question question) {
    this.valid = valid;
    this.question = question;
  }

  private Result(boolean valid, Answer answer, Question question) {
    this.valid = valid;
    this.answer = answer;
    this.question = question;
  }


  public static Result ok() {
    return new Result(true, "null");
  }

  public static Result ok(Answer answer) {
    return new Result(true, answer);
  }

  public static Result ok(Question question) {
    return new Result(true, question);
  }

  public static Result ok(Answer answer, Question question
  ) {
    return new Result(true, answer, question);
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

