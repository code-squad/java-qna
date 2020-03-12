package com.codessquad.qna.commons;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {
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

  public Result(Object valid, Object errorMessage, Object object) {
    this.valid = (boolean) valid;
    this.errorMessage = (String) errorMessage;
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
