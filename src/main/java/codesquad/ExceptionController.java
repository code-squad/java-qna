package codesquad;

import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.slf4j.LoggerFactory.getLogger;

@ControllerAdvice
public class ExceptionController {
    private static final Logger log = getLogger(ExceptionController.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String exception(RuntimeException e, Model model) {
        log.info("에러잡았다.");
        model.addAttribute("error", e.getMessage());
        return "/user/login";
    }
}
