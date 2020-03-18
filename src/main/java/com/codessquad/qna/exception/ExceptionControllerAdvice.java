package com.codessquad.qna.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

  @ExceptionHandler(UnauthorizedException.class)
  public String HandleError(UnauthorizedException exception) {
    return exception.getUrl();
  }

  @ExceptionHandler(NoSuchUserException.class)
  public String HandleError(NoSuchUserException exception) {
    return exception.getUrl();
  }

  @ExceptionHandler(NoSuchElementException.class)
  public String HandleError(NoSuchElementException exception) {
    return exception.getUrl();
  }

  @ExceptionHandler(IllegalFormatArgumentException.class)
  public String HandleError(IllegalFormatArgumentException exception) {
    return exception.getUrl();
  }
}