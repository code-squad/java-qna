package com.codessquad.qna.exception;

public class RudimentaryException extends RuntimeException {
  private String url;
  private String errorMessage;

  public RudimentaryException(String url, String errorMessage) {
    this.url = url;
    this.errorMessage = errorMessage;
  }

  public String getUrl() {
    return url;
  }

  public String getErrorMessage() {
    return errorMessage;
  }
}