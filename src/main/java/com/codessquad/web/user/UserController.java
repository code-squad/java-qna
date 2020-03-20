package com.codessquad.web.user;

import com.codessquad.common.HttpSessionUtils;
import com.codessquad.domain.user.User;
import com.codessquad.domain.user.UserRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @GetMapping("/loginFailedForm")
    public String loginFailedForm() {
        return "/user/login_failed";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return "redirect:/users/loginFailedForm";
        }
        if (!user.matchPasword(password)) {
            return "redirect:/users/loginFailedForm";
        }
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

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", userRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자입니다.")));
        return "/user/profile";
    }

    @GetMapping("{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        try {
            User loginUser = HttpSessionUtils.getUserFromSession(session);
            hasPermission(id, session);
            model.addAttribute("user", loginUser);
            return "/user/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{id}")
    public String update(@PathVariable Long id, User newUser, HttpSession session, Model model) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            return "redirect:/users/loginForm";
        }
        User user = userRepository.findById(id).get();
        user.update(newUser);
        userRepository.save(user);
        return String.format("redirect:/users/%d", id);
    }

    private boolean hasPermission(Long id, HttpSession session) {
        if (!HttpSessionUtils.isLoginUser(session)) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        if (!loginUser.matchId(id)) {
            throw new IllegalStateException("자신의 정보만 수정 가능합니다.");
        }
        return true;
    }
}
