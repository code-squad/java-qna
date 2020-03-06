package com.codesquad.qna.web;

import com.codesquad.qna.NotFoundError;
import com.codesquad.qna.domain.User;
import com.codesquad.qna.domain.UserRepository;
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
    private static final String USER_LIST = "redirect:/users/list";

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User sessionUser = userRepository.findByUserId(userId);

        if (sessionUser == null) {
            return "redirect:/users/loginForm";
        }

        if (!sessionUser.isEqualsPassword(password)) {
            return "redirect:/users/loginForm";
        }

        session.setAttribute("sessionUser", sessionUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionUser");
        return "redirect:/";
    }

    @GetMapping("/form")
    public String createForm() {
        return "user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        userRepository.save(user);
        return USER_LIST;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/{id}/form")
    public String checkPasswordForm(@PathVariable Long id, Model model) throws NotFoundError {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundError(NotFoundError.NOT_FOUND_MESSAGE));
        model.addAttribute("user", user);
        return "user/checkForm";
    }

    @PostMapping("/{id}/checkPassword")
    public String checkPassword(@PathVariable Long id, User updateUser, Model model) throws NotFoundError {
        User user = findUserById(id);
        if (user.isEqualsPassword(updateUser)) {
            model.addAttribute("user", user);
            return "user/updateForm";
        }
        return USER_LIST;
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User updateUser) throws NotFoundError {
        User user = findUserById(id);
        user.update(updateUser);
        userRepository.save(user);
        return USER_LIST;
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) throws NotFoundError {
        User user = findUserById(id);
        model.addAttribute("user", user);
        return "user/profile";
    }

    public User findUserById(Long id) throws NotFoundError {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundError(NotFoundError.NOT_FOUND_MESSAGE));
    }
}
