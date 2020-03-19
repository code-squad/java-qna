package com.codessquad.qna.exception;

public class NoSuchPostException extends RudimentaryException {
  public NoSuchPostException(String url, String errorMessage) {
    super(url, errorMessage);
  }
}
