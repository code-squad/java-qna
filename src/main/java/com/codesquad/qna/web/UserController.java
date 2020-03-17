package com.codesquad.qna.web;

import com.codesquad.qna.advice.exception.ExistUserException;
import com.codesquad.qna.advice.exception.InputMistakeException;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static com.codesquad.qna.web.HttpSessionUtils.USER_SESSION_KEY;
import static com.codesquad.qna.web.HttpSessionUtils.checkLogin;
import static com.codesquad.qna.web.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/users")
public class UserController {
    public static final String REDIRECT_LOGIN_FORM = "redirect:/users/login";
    private static final String REDIRECT_USERS_DATA = "redirect:/users";

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userService.findByUserId(userId);

        if (!user.isPasswordEquals(password)) {
            return REDIRECT_LOGIN_FORM;
        }

        session.setAttribute(USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createForm() {
        return "/user/form";
    }

    @PostMapping("")
    public String create(User user, Model model) {
        User createdUser = Optional.ofNullable(user).orElseThrow(InputMistakeException::enteredNull);
        userService.save(createdUser);
        return REDIRECT_USERS_DATA;
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/update")
    public String checkPasswordForm(@PathVariable Long id, Model model, HttpSession session) {
        checkLogin(session);

        User sessionedUser = getUserFromSession(session);
        sessionedUser.hasPermission(id);

        model.addAttribute("user", sessionedUser);
        return "/user/checkForm";
    }

    @PostMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, String password, Model model, HttpSession session) {
        checkLogin(session);

        User sessionedUser = getUserFromSession(session);
        sessionedUser.hasPermission(id);

        if (!sessionedUser.isPasswordEquals(password)) {
            return "redirect:/users/{id}";
        }

        model.addAttribute("user", sessionedUser);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User updateUser, HttpSession session) {
        checkLogin(session);

        User sessionedUser = getUserFromSession(session);
        sessionedUser.hasPermission(id);
        sessionedUser.update(updateUser);

        userService.save(sessionedUser);
        return REDIRECT_USERS_DATA;
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @ExceptionHandler(ExistUserException.class)
    public String loginError() {
        return "redirect:/users/login";
    }

    @ExceptionHandler(InputMistakeException.class)
    public String inputError() {
        return "redirect:/users/create";
    }
}
