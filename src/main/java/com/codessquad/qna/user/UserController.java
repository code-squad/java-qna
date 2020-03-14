package com.codessquad.qna.user;

import com.codessquad.qna.utils.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        log.info("User :  '{}' ", user);
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            log.info("login null Failure");
            return "redirect:/users/loginForm";
        }
        if (!user.matchPassword(password)) {
            log.info("login Failure");
            return "redirect:/users/loginForm";
        }

        log.info("login success");
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form() {
        return "user/form";
    }

    @GetMapping("")
    public String userList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(IllegalStateException::new));
        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionUser.matchId(id)) {
            throw new IllegalStateException("자신의 정보만 수정하세요.");
        }

        User user = userRepository.findById(id).orElseThrow(IllegalStateException::new);
        model.addAttribute("user", user);
        return "user/updateForm";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, User updateUser, HttpSession session) {
        if (HttpSessionUtils.isNoneExistentUser(session)) {
            return "redirect:/users/loginForm";
        }
        User sessionUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionUser.matchId(id)) {
            throw new IllegalStateException("자신의 정보만 수정하세요.");
        }

        User user = userRepository.findById(id).orElseThrow(IllegalStateException::new);
        user.update(updateUser);
        userRepository.save(user);
        return "redirect:/users";
    }
}
