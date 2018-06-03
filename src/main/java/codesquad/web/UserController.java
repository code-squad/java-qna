package codesquad.web;


import codesquad.domain.Result;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.Optional;

import static codesquad.web.SessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserRepository userRepository;

    @GetMapping()
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "list";
    }

    @PostMapping()
    public String create(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));

        return "profile";
    }

    @PutMapping("/{id}")
    public String updateUserData(@PathVariable Long id, User updateUser, String newPassword, HttpSession session) {
        log.debug("updateUser : {}", updateUser);

        User sessionUser = SessionUtils.getUserFromSession(session);
        sessionUser.isMatchedUser(updateUser);
        User oldUser = userRepository.findOne(id);
        oldUser.update(updateUser, newPassword);
        userRepository.save(oldUser);

        return "redirect:/users";
    }

    @GetMapping("/{id}/form")
    public String getUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
//        Result result = valid(session);
//        if (!result.isValid()) {
//            model.addAttribute("errorMessage", result.getMessage());
//            return "/user/login";
//        }

        User user = userRepository.findOne(id);
        User sessionUser = SessionUtils.getUserFromSession(session);
        user.isMatchedUser(sessionUser);
        model.addAttribute("user", user);

        return "user/updateForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(HttpServletRequest request, Model model) {
        String errorMessage = (String)request.getAttribute("errorMessage");
        log.debug("errorMessage : {}", errorMessage);
        model.addAttribute("errorMessage", Optional.ofNullable(errorMessage).orElse(""));
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session, Model model) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            model.addAttribute("errorMessage", Result.MISMATCH_ID.getMessage());
            return "/user/login";
        }
        if (!user.isMatchedPassword(password)) {
            model.addAttribute("errorMessage", Result.MISMATCH_PWD.getMessage());
            return "/user/login";
        }
        log.debug("Login success !!");
        session.setAttribute(USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    private Result valid(HttpSession session) {
        if (!SessionUtils.isLoginUser(session)) {
            return Result.NEED_LOGIN;
        }
        return Result.SUCCESS;
    }

    private Result valid(HttpSession session, User user) {
        Result result = valid(session);
        if (!result.isValid()) {
            return result;
        }

        User sessionUser = SessionUtils.getUserFromSession(session);
        if (!user.isMatchedPassword(sessionUser)) {
            return Result.MISMATCH_USER;
        }

        return result;
    }
}
