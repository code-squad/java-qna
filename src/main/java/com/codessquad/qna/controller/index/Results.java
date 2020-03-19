package com.codessquad.qna.controller.index;

public class Results {

  private boolean valid;
  private String errorMsg;

  private Results(boolean valid, String errorMsg) {
    this.valid = valid;
    this.errorMsg = errorMsg;
  }

  public boolean isValid() {
    return valid;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public static Results ok() {
    return new Results(true, null);
  }

  public static Results fail(String errorMsg) {
    return new Results(false, errorMsg);
  }
}