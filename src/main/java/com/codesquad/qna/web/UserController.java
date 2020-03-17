package com.codesquad.qna.web;

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
import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.codesquad.qna.UrlStrings.REDIRECT_LOGIN_FORM;
import static com.codesquad.qna.UrlStrings.REDIRECT_USERS_DATA;
import static com.codesquad.qna.web.HttpSessionUtils.USER_SESSION_KEY;
import static com.codesquad.qna.web.HttpSessionUtils.checkLogin;
import static com.codesquad.qna.web.HttpSessionUtils.getUserFromSession;

@Controller
@RequestMapping("/users")
public class UserController {

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
            return REDIRECT_LOGIN_FORM.getUrl();
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
        User createdUser = Optional.ofNullable(user).orElseThrow(IllegalArgumentException::new);
        userService.save(createdUser);
        return REDIRECT_USERS_DATA.getUrl();
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/update")
    public String checkPasswordForm(@PathVariable Long id, Model model, HttpSession session) {
        checkLogin(session);

        User loginUser = getUserFromSession(session);
        loginUser.hasPermission(id);

        model.addAttribute("user", loginUser);
        return "/user/checkForm";
    }

    @PostMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, String password, Model model, HttpSession session) {
        checkLogin(session);

        User loginUser = getUserFromSession(session);
        loginUser.hasPermission(id);

        if (!loginUser.isPasswordEquals(password)) {
            return "redirect:/users/{id}";
        }

        model.addAttribute("user", loginUser);
        return "/user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User updateUser, HttpSession session) {
        checkLogin(session);
        User loginUser = getUserFromSession(session);
        userService.update(id, loginUser, updateUser);
        return REDIRECT_USERS_DATA.getUrl();
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "/user/profile";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String noInput() {
        return "redirect:/users/create";
    }
}
