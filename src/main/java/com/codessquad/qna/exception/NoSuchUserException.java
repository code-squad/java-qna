package com.codessquad.qna.exception;

public class NoSuchUserException extends RudimentaryException {

  public NoSuchUserException(String url, String errorMessage) {
    super(url, errorMessage);
  }
}
