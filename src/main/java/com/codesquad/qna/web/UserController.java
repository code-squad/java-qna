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
    private static final String REDIRECT_USERS_LIST = "redirect:/users/list";

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User sessionedUser = userRepository.findByUserId(userId);

        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }

        if (!sessionedUser.isEqualsPassword(password)) {
            return "redirect:/users/loginForm";
        }

        session.setAttribute("sessionedUser", sessionedUser);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("sessionedUser");
        return "redirect:/";
    }

    @GetMapping("/createForm")
    public String createForm() {
        return "/user/form";
    }

    @PostMapping("/create")
    public String create(User user) {
        if (user == null) {
            throw new NullPointerException();
        }
        userRepository.save(user);
        return REDIRECT_USERS_LIST;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "/user/list";
    }

    @GetMapping("/{id}/checkForm")
    public String checkPasswordForm(@PathVariable Long id, Model model, HttpSession session) throws NotFoundError {
        User sessionedUser = (User) session.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }

        User user = findUserByObject(sessionedUser);

        model.addAttribute("user", user);
        return "/user/checkForm";
    }

    @PostMapping("/{id}/checkPassword")
    public String checkPassword(@PathVariable Long id, User updateUser, Model model, HttpSession session) throws NotFoundError {
        User sessionedUser = (User) session.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }

        User user = findUserByObject(sessionedUser);

        if (user.isEqualsPassword(updateUser)) {
            model.addAttribute("user", user);
            return "/user/updateForm";
        }
        return REDIRECT_USERS_LIST;
    }

    @PostMapping("/{id}/update")
    public String updateUser(@PathVariable Long id, User updateUser, HttpSession session) throws NotFoundError {
        User sessionedUser = (User) session.getAttribute("sessionedUser");

        if (sessionedUser == null) {
            return "redirect:/users/loginForm";
        }

        User user = findUserByObject(sessionedUser);

        user.update(updateUser);
        userRepository.save(user);
        return REDIRECT_USERS_LIST;
    }

    @GetMapping("/{id}")
    public String read(@PathVariable Long id, Model model) throws NotFoundError {
        User user = findUserById(id);
        model.addAttribute("user", user);
        return "/user/profile";
    }

    private User findUserById(Long id) throws NotFoundError {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundError(NotFoundError.NOT_FOUND_MESSAGE));
    }

    private User findUserByObject(User user) throws NotFoundError {
        return userRepository.findById(user.getId()).orElseThrow(() -> new NotFoundError(NotFoundError.NOT_FOUND_MESSAGE));
    }
}
