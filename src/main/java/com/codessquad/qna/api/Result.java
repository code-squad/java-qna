package com.codessquad.qna.api;

import lombok.Data;

@Data
public class Result {
  private boolean valid;
  private String errorMessage;
  private Object object;

  private Result(boolean valid, String errorMessage) {
    this.valid = valid;
    this.errorMessage = errorMessage;
  }

  private Result(boolean valid, String errorMessage, Object object) {
    this.valid = valid;
    this.errorMessage = errorMessage;
    this.object = object;
  }

  public static Result ok() {
    return new Result(true, null);
  }

  public static Result ok(Object object) {
    return new Result(true, null, object);
  }

  public static Result fail(String errorMessage) {
    return new Result(false, errorMessage);
  }
}
