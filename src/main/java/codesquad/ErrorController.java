package codesquad;

import codesquad.exceptions.PasswordMismatchException;
import codesquad.exceptions.UnauthorizedRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(ErrorController.class);

    @ExceptionHandler({UnauthorizedRequestException.class, PasswordMismatchException.class})
    public String handleUnauthorizedException(Model model, Throwable e) {
        logger.debug(e.getMessage());
        model.addAttribute("errorMessage", e.getMessage());
        return "/error";
    }

}
