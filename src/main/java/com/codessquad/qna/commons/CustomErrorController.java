package com.codessquad.qna.commons;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

  private static final String ERROR_PATH = "/error";

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  @RequestMapping("")
  public String handleError(HttpServletRequest request, Model model) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    int statusCode = Integer.parseInt(Optional.of(status.toString()).orElse(HttpStatus.NOT_FOUND.toString()));
    HttpStatus httpStatus = HttpStatus.valueOf(statusCode);

    model.addAttribute("code", statusCode);
    model.addAttribute("msg", httpStatus.getReasonPhrase());

    return "errors/errorPage";
  }
}
