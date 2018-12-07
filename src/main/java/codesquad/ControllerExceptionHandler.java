package codesquad;

import codesquad.domain.user.UserRepository;
import codesquad.exception.AnswerException;
import codesquad.exception.ListFailedException;
import codesquad.exception.UpdatefailedException;
import codesquad.exception.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);
    @Autowired
    private UserRepository userRepository;

    @ExceptionHandler(value = AnswerException.class)
    public String handleBaseException() {
        return "/abc";
    }

    @ExceptionHandler(value = UserException.class)
    public String loginExcetion(RuntimeException ex, Model model) {
        logger.debug(ex.getMessage());
        model.addAttribute("errorMessage", ex.getMessage());
        return "/user/login";
    }

    @ExceptionHandler(value = UpdatefailedException.class)
    public String updatefailedException(RuntimeException ex, Model model, HttpSession session) {
        logger.debug("update_failed");
        model.addAttribute("user", HttpSessionUtils.getUserFormSession(session));
        model.addAttribute("errorMessage", ex.getMessage());
        return "/user/updateForm";
    }

    @ExceptionHandler(value = ListFailedException.class)
    public String listFailedException(RuntimeException ex, Model model) {
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("errorMessage", ex.getMessage());
        return "/user/list";
    }


}