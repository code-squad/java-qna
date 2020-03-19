package com.codessquad.qna.exception;

public class NoSuchAnswerException extends RudimentaryException {
  public NoSuchAnswerException(String url, String errorMessage) {
    super(url, errorMessage);
  }
}