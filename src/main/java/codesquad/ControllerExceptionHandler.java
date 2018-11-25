package codesquad;

import codesquad.exception.*;
import codesquad.user.UserRepository;
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
    public String handleBaseException(){
        return "/abc";
    }

    @ExceptionHandler(value = UserIsNotLoginException.class)
    public String loginExcetion() {
        return "redirect:/users/login";
    }

    @ExceptionHandler(value = LoginUseIsNotMatchException.class)
    public String notMatchUserExcetion() {
        return "/user/notMatchUser";
    }

    @ExceptionHandler(value = UserException.class)
    public String userExceotion() {
        return "/user/login_failed";
    }

    @ExceptionHandler(value = UpdatefailedException.class)
    public String updatefailedException(Model model, HttpSession session) {
        logger.debug("update_failed");
        model.addAttribute("user", HttpSessionUtils.getUserFormSession(session));
        return "/user/update_failed";
    }

    @ExceptionHandler(value = ListFailedException.class)
    public String listFailedException(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list_failed";
    }



}