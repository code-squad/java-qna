package com.codesquad.qna.web;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    public static final String REDIRECT_USERS_LIST = "redirect:/users/list";

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (!optionalUser.isPresent()) {
            return "redirect:/users/loginForm";
        }

        User user = optionalUser.get();
        if (!user.isPasswordEquals(password)) {
            return "redirect:/users/loginForm";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/createForm")
    public String createForm() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        Optional<User> optionalUser = Optional.ofNullable(user);
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException();
        }
        userRepository.save(user);
        return REDIRECT_USERS_LIST;
    }

    @GetMapping("/list")
    public String list(Model model, HttpSession session) {
        Optional<Object> sessionedUser = HttpSessionUtils.getObject(session);
        if (!sessionedUser.isPresent()) {
            return "/users/loginForm";
        }

        User user = (User) sessionedUser.get();
        model.addAttribute("user", user);
        return "/user/list";
    }

    @GetMapping("/{id}/checkForm")
    public String checkPasswordForm(@PathVariable Long id, Model model, HttpSession session) throws IllegalAccessException {
        Optional<Object> sessionedUser = HttpSessionUtils.getObject(session);
        if (!sessionedUser.isPresent()) {
            return "/users/loginForm";
        }

        User user = (User) sessionedUser.get();
        user.checkIllegalAccess(id);

        model.addAttribute("user", user);
        return "/user/checkForm";
    }

    @PostMapping("/{id}/updateForm")
    public String updateForm(@PathVariable Long id, String password, Model model, HttpSession session) throws IllegalAccessException {
        Optional<Object> sessionedUser = HttpSessionUtils.getObject(session);
        if (!sessionedUser.isPresent()) {
            return "/users/loginForm";
        }

        User user = (User) sessionedUser.get();
        user.checkIllegalAccess(id);

        if (!user.isPasswordEquals(password)) {
            return "redirect:/users/{id}/checkForm";
        }

        model.addAttribute("user", user);
        return "/user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User updateUser, HttpSession session) throws IllegalAccessException {
        Optional<Object> sessionedUser = HttpSessionUtils.getObject(session);
        if (!sessionedUser.isPresent()) {
            return "/users/loginForm";
        }

        User user = (User) sessionedUser.get();
        user.checkIllegalAccess(id);
        user.update(updateUser);
        userRepository.save(user);
        return REDIRECT_USERS_LIST;
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        model.addAttribute("user", user);
        return "/user/profile";
    }
}
