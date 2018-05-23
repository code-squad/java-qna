package codesquad.controller;

import codesquad.domain.exception.CustomException;
import codesquad.domain.exception.ForbiddenRequestException;
import codesquad.domain.exception.UnAuthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErrorController {
    private static final Logger log = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler({UnAuthorizedException.class, ForbiddenRequestException.class})
    public ModelAndView handleException(CustomException e) {
        log.error(" ERROR OCCUR : {} ", e.getMessage());
        ModelAndView modelAndView = new ModelAndView("/error/err.html");
        modelAndView.setStatus(e.getStatus());
        return modelAndView;
    }
}
