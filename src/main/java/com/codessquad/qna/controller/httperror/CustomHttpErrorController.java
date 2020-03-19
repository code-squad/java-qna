package com.codessquad.qna.exception;

import ch.qos.logback.classic.Logger;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomHttpErrorController implements ErrorController {

  private static final String ERROR_PATH = "/httperror";
  Logger logger = (Logger) LoggerFactory.getLogger(CustomHttpErrorController.class);

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @RequestMapping("/httperror")
  public String HandleError(HttpServletRequest request, Model model) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    logger.info("status: " + status);
    HttpStatus httpStatus = HttpStatus.valueOf(status.toString());

    logger.info("httpStatus : " + httpStatus.toString());
    model.addAttribute("code", status.toString());
    model.addAttribute("msg", httpStatus.getReasonPhrase());
    model.addAttribute("timestamp", new Date());

    return "error/error";
  }
}