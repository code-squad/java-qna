package com.codessquad.qna.exception;

public class IllegalFormatArgumentException extends RudimentaryException {

  public IllegalFormatArgumentException(String url, String errorMessage) {
    super(url, errorMessage);
  }
}
