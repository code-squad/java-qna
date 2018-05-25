package codesquad;

import codesquad.exceptions.NoSessionedUserException;
import codesquad.exceptions.PageNotFoundException;
import codesquad.exceptions.PasswordMismatchException;
import codesquad.exceptions.UnauthorizedRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler({UnauthorizedRequestException.class, PageNotFoundException.class})
    public String handleUnauthorizedException(Model model, Throwable e) {
        logger.debug(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "/error";
    }

    @ExceptionHandler(PasswordMismatchException.class)
    public String handlePasswordMismatchException(Throwable e) {
        logger.debug("Wrong password: " + e.getMessage());
        return "/users/login_failed";
    }

    @ExceptionHandler(NoSessionedUserException.class)
    public String handleNoSessionedUserException(Throwable e) {
        logger.debug("User is not logged in: " + e.getMessage());
        return "/users/login";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException() {
        logger.debug("Failed to create User instance");
        return "/users/login";
    }
}