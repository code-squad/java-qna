package com.codessquad.qna.exception;

public class NoSuchElementException extends RudimentaryException {
  public NoSuchElementException(String url, String errorMessage) {
    super(url, errorMessage);
  }
}
