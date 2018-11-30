package codesquad.config;

import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.slf4j.LoggerFactory.getLogger;

@ControllerAdvice
public class ExceptionController {
    private static final Logger log = getLogger(ExceptionController.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String longException(RuntimeException e, Model model) {
        model.addAttribute("error", e.getMessage());
        return e.getMessage();
    }
}
