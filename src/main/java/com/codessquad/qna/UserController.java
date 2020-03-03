package com.codessquad.qna;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String createUser(User user) {
        userRepository.save(user);

        return "redirect:/users";
    }

    @GetMapping("")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "user/list";
    }

    @GetMapping("/form")
    public String goForm() {
        return "user/form";
    }

    @GetMapping("/{id}")
    public String showUserProfile(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("userProfile", user);

        return "user/profile";
    }

    @GetMapping("/{id}/form")
    public String modifyUserProfile(@PathVariable Long id, Model model, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        assert loginUser != null;
        if (!loginUser.matchId(id)) {
            throw new IllegalStateException("자기 자신의 정보만 수정 가능합니다.");
        }
        User user = userRepository.findById(id).orElseThrow(NoSuchElementException::new);
        model.addAttribute("userProfile", user);

        return "user/updateForm";
    }

    @PutMapping("/{id}/update")
    public String updateUserProfile(@PathVariable Long id, Model model, User updateUser, HttpSession session) {
        if (!HttpSessionUtils.isLoggedInUser(session)) {
            return "redirect:/users/login";
        }
        User loginUser = HttpSessionUtils.getUserFromSession(session);
        assert loginUser != null;
        if (!loginUser.matchId(id)) {
            throw new IllegalStateException("자기 자신의 정보만 수정 가능합니다.");
        }
        if (loginUser.matchPassword(updateUser)) {
            loginUser.update(updateUser);
            userRepository.save(loginUser);
        }
        model.addAttribute("userProfile", loginUser);

        return "redirect:/users";
    }

    @GetMapping("/login")
    public String goLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, Model model, HttpSession session) {
        User loginUser = userRepository.findByUserId(userId);
        if (loginUser == null || !loginUser.matchPassword(password)) {
            model.addAttribute("userId", userId);

            return "user/login_failed";
        }
        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, loginUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }
}
