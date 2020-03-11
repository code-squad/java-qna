package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession httpSession, Model model) {
        try {
            User user = userRepository.findByUserId(userId);
            hasPermission(user, password);
            httpSession.setAttribute(HttpSessionUtils.USER_SESSION_ID, user);
            return "redirect:/";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_ID);
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/create")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("")
    public String users(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}")
    public String profile(@PathVariable Long id, Model model) {
        model.addAttribute("user", findUser(userRepository, id));
        return "user/profile";
    }

    @GetMapping("/form")
    public String userForm() {
        return "user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model, HttpSession httpSession) {
        try {
            hasPermission(httpSession, id);
            model.addAttribute("user", findUser(userRepository, id));
            return "user/updateForm";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, String password, String newPassword,
                             String checkPassword, String name, String email,
                             HttpSession httpSession, Model model) {
        try {
            hasPermission(httpSession, id);
            User user = findUser(userRepository, id);

            if (user.notMatchPassword(password)) {
                return "redirect:/users/{id}/form";
            }

            if (newPassword.equals(checkPassword)) {
                user.update(name, email, newPassword);
                userRepository.save(user);
                return "redirect:/users";
            }
            return "redirect:/users/{id}/form";
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "/user/login";
        }
    }

    private User findUser(UserRepository userRepository, Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("There is no user."));
    }

    private void hasPermission(User user, String password) {
        if (!HttpSessionUtils.isLoginUser(user)) {
            throw new IllegalStateException("일치하는 아이디가 없습니다.");
        }
        if (user.notMatchPassword(password)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void hasPermission(HttpSession httpSession, Long id) {
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);

        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            throw new IllegalStateException("일치하는 아이디가 없습니다.");
        }

        if (sessionedUser.notMatchId(id)) {
            throw new IllegalStateException("다른 사용자의 정보는 변경할 수 없습니다.");
        }
    }
}
