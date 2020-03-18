package com.codessquad.qna.exception;

public class UnauthorizedException extends RudimentaryException {

  public UnauthorizedException(String url, String errorMessage) {
    super(url, errorMessage);
  }
}