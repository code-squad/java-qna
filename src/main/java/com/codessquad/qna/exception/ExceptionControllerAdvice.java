package com.codessquad.qna.exception;

import ch.qos.logback.classic.Logger;
import com.codessquad.qna.controller.users.UsersAPIController;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {
  Logger logger = (Logger) LoggerFactory.getLogger(UsersAPIController.class);

  @ExceptionHandler(UnauthorizedException.class)
  public String HandleError(UnauthorizedException exception) {
    logger.error("unauthorized exception");
    return exception.getUrl();
  }

  @ExceptionHandler(NoSuchUserException.class)
  public String HandleError(NoSuchUserException exception) {
    logger.error("no such user exception.");
    return exception.getUrl();
  }

  @ExceptionHandler(NoSuchPostException.class)
  public String HandleError(NoSuchPostException exception) {
    logger.error("no such post exception");
    return exception.getUrl();
  }

  @ExceptionHandler(IllegalFormatArgumentException.class)
  public String HandleError(IllegalFormatArgumentException exception) {
    logger.error("illegal format argument exception");
    return exception.getUrl();
  }
}