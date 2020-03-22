package com.codessquad.qna;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;

import static com.codessquad.qna.HttpSessionUtils.isLogin;
import static com.codessquad.qna.HttpSessionUtils.getUserFromSession;
import static com.codessquad.qna.HttpSessionUtils.USER_SESSION_KEY;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/form")
    public String viewUserForm() {
        return "/users/form";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/loginForm")
    public String viewLoginForm() {
        return "/users/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        try {
            User user = findUser(userId);
            if (!user.isPasswordEquals(password)) {
                return "redirect:/users/loginForm";
            }
            session.setAttribute(USER_SESSION_KEY, user);
            return "redirect:/";
        } catch (EntityNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("")
    public String viewList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/users/list";
    }

    @GetMapping("/{id}")
    public String viewProfile(@PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("user", getSessionUser(id, session));
            return "/users/profile";
        } catch (IllegalAccessException e) {
            log.info("Error Code > {} ", e.toString());
            return e.getMessage();
        }
    }

    @GetMapping("/{id}/form")
    public String viewUpdateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            model.addAttribute("user", getSessionUser(id, session));
            return "/users/updateForm";
        } catch (IllegalAccessException e) {
            log.info("Error Code > {} ", e.toString());
            return e.getMessage();
        }
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, String password, String name, String email, HttpSession session) {
        try {
            User user = getSessionUser(id, session);
            if (!user.isPasswordEquals(password)) {
                return "redirect:/users";
            }
            user.update(name, email);
            userRepository.save(user);
            return "redirect:/users";
        } catch (IllegalAccessException e) {
            log.info("Error Code > {} ", e.toString());
            return e.getMessage();
        }
    }

    private User findUser(String userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new EntityNotFoundException("redirect:/users/loginForm");
        }
        return user;
    }

    private User getSessionUser(Long id, HttpSession session) throws IllegalAccessException {
        if (!isLogin(session)) {
            throw new IllegalAccessException("/error/unauthorized");
        }
        User sessionUser = getUserFromSession(session);
        if (!sessionUser.isIdEquals(id)) {
            throw new IllegalAccessException("/error/forbidden");
        }
        return sessionUser;
    }
}
