package com.codessquad.qna;

import com.codessquad.qna.domain.User;
import com.codessquad.qna.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String login(String userId, String password, HttpSession httpSession) {
        User user = userRepository.findByUserId(userId);

        if (!HttpSessionUtils.isLoginUser(user)) {
            return "redirect:/users/loginForm";
        }
        if (user.notMatchPassword(password)) {
            return "redirect:/users/loginForm";
        }

        httpSession.setAttribute(HttpSessionUtils.USER_SESSION_ID, user);
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_ID);
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
        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);
        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            return "redirect:/users/loginForm";
        }

        if (sessionedUser.notMatchId(id)) {
            throw new IllegalStateException("Yon can't update another user.");
        }

        model.addAttribute("user", findUser(userRepository, id));
        return "user/updateForm";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id,
                             @RequestParam String password,
                             @RequestParam String newPassword,
                             @RequestParam String checkPassword,
                             @RequestParam String name,
                             @RequestParam String email,
                             HttpSession httpSession) {

        User sessionedUser = HttpSessionUtils.getUserFromSession(httpSession);
        if (!HttpSessionUtils.isLoginUser(sessionedUser)) {
            return "redirect:/users/loginForm";
        }

        if (sessionedUser.notMatchId(id)) {
            throw new IllegalStateException("Yon can't update another user.");
        }

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
    }

    private User findUser(UserRepository userRepository, Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("There is no user."));
    }
}
