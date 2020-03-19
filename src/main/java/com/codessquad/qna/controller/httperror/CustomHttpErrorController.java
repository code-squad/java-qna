package com.codessquad.qna.controller.httperror;

import ch.qos.logback.classic.Logger;
import java.util.Date;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomHttpErrorController implements ErrorController {

  private static final String ERROR_PATH = "/error";
  Logger logger = (Logger) LoggerFactory.getLogger(CustomHttpErrorController.class);

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @RequestMapping("/error")
  public String handleError(HttpServletResponse response, Model model) {
    int status = response.getStatus();
    logger.info("status: " + status);
    model.addAttribute("code", status);
    model.addAttribute("timestamp", new Date());
    return "error/error";
  }
}